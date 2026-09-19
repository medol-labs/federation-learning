package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.profileagentdataset

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.CsvDatasetProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.resolveRuntimeDatasetPath
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetService
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Path

@Component
class CsvFileProfileAgentDatasetAdapter(
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository,
    private val properties: CsvDatasetProperties,
    @Value("\${runtime-agent.local-runtime-engine.dataset-host-root:../../volumes/datasets}")
    private val datasetHostRoot: String = "../../volumes/datasets",
    @Value("\${runtime-agent.local-runtime-engine.dataset-container-root:/workspace/datasets}")
    private val datasetContainerRoot: String = "/workspace/datasets"
) : ProfileAgentDatasetService {
    override fun execute(input: ProfileAgentDatasetInput): ProfileAgentDatasetResult {
        val binding = bindingRepository.findAll(Pageable.unpaged())
            .content
            .filter { it.runtimeDatasetBindingId == input.runtimeDatasetBindingId || it.datasetId == input.datasetId }
            .filter { it.isCsvFileBinding() }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }
            ?: return rejected("CSV runtime dataset binding was not found for binding ${input.runtimeDatasetBindingId}.")

        val filePath = binding.filePath?.trim()
        if (filePath.isNullOrEmpty()) {
            return rejected("CSV runtime dataset binding filePath is required.")
        }

        val path = resolveRuntimeDatasetPath(filePath, datasetHostRoot, datasetContainerRoot)
        if (!Files.isRegularFile(path)) {
            return rejected("CSV dataset file does not exist: $filePath")
        }
        if (!Files.isReadable(path)) {
            return rejected("CSV dataset file is not readable: $filePath")
        }

        val expectedLabelColumns = properties.normalizedLabelColumns()
        val profile = readCsvProfile(path, expectedLabelColumns)
            ?: return rejected("CSV dataset file is empty: $filePath")

        if (profile.headerColumns.isEmpty()) {
            return rejected("CSV dataset file has no readable header: $filePath")
        }

        return ProfileAgentDatasetResult.Succeeded(
            sampleCount = profile.sampleRowCount,
            featureCount = profile.headerColumns.size,
            schemaCompatible = profile.invalidRowCount == 0,
            labelCompatible = profile.labelIndex != null,
            missingValueRate = profile.missingValueRate(),
            duplicateRate = profile.duplicateRate(),
            qualityScore = profile.qualityScore(),
            nonIidScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            classBalanceScore = profile.classBalanceScore()
        )
    }

    private fun rejected(reason: String): ProfileAgentDatasetResult.Rejected =
        ProfileAgentDatasetResult.Rejected(failureReason = reason)

    private fun RuntimeDatasetBindingCatalogReadModel.isCsvFileBinding(): Boolean {
        val format = dataFormat.normalized()
        return format == "csv"
    }

    private fun readCsvProfile(path: Path, expectedLabelColumns: Set<String>): CsvProfile? {
        Files.newBufferedReader(path).useLines { lines ->
            val iterator = lines.iterator()
            val header = iterator.nextNonBlankLine() ?: return null
            val headerColumns = header.toCsvColumns().filter { it.isNotBlank() }
            val labelIndex = headerColumns.indexOfFirst { it.normalized() in expectedLabelColumns }.takeIf { it >= 0 }
            val rows = mutableSetOf<String>()
            val labelCounts = mutableMapOf<String, Int>()
            var sampleRowCount = 0
            var invalidRowCount = 0
            var duplicateRowCount = 0
            var missingCellCount = 0
            var totalCellCount = 0

            while (iterator.hasNext()) {
                val row = iterator.next().trim()
                if (row.isBlank()) {
                    continue
                }
                sampleRowCount += 1
                if (!rows.add(row)) {
                    duplicateRowCount += 1
                }
                val cells = row.toCsvColumns()
                if (cells.size != headerColumns.size) {
                    invalidRowCount += 1
                }
                totalCellCount += headerColumns.size
                missingCellCount += cells.take(headerColumns.size).count { it.isBlank() }
                if (cells.size < headerColumns.size) {
                    missingCellCount += headerColumns.size - cells.size
                }
                if (labelIndex != null && labelIndex < cells.size) {
                    val label = cells[labelIndex].trim()
                    if (label.isNotEmpty()) {
                        labelCounts[label] = (labelCounts[label] ?: 0) + 1
                    }
                }
            }

            return CsvProfile(
                headerColumns = headerColumns,
                sampleRowCount = sampleRowCount,
                invalidRowCount = invalidRowCount,
                duplicateRowCount = duplicateRowCount,
                missingCellCount = missingCellCount,
                totalCellCount = totalCellCount,
                labelIndex = labelIndex,
                labelCounts = labelCounts
            )
        }
    }

    private fun Iterator<String>.nextNonBlankLine(): String? {
        while (hasNext()) {
            val line = next().trim()
            if (line.isNotEmpty()) {
                return line
            }
        }
        return null
    }

    private fun String.toCsvColumns(): List<String> =
        split(',').map { it.trim().trim('"') }

    private fun String?.normalized(): String =
        this?.trim()?.lowercase()?.replace("-", "_") ?: ""

    private fun CsvDatasetProperties.normalizedLabelColumns(): Set<String> =
        labelColumns.map { it.normalized() }.filter { it.isNotBlank() }.toSet()

    private data class CsvProfile(
        val headerColumns: List<String>,
        val sampleRowCount: Int,
        val invalidRowCount: Int,
        val duplicateRowCount: Int,
        val missingCellCount: Int,
        val totalCellCount: Int,
        val labelIndex: Int?,
        val labelCounts: Map<String, Int>
    ) {
        fun missingValueRate(): BigDecimal =
            ratio(missingCellCount, totalCellCount)

        fun duplicateRate(): BigDecimal =
            ratio(duplicateRowCount, sampleRowCount)

        fun qualityScore(): BigDecimal {
            if (sampleRowCount == 0 || totalCellCount == 0) {
                return BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP)
            }
            val invalidPenalty = ratio(invalidRowCount, sampleRowCount)
            return (BigDecimal.ONE - invalidPenalty - missingValueRate() - duplicateRate())
                .coerceIn(BigDecimal.ZERO, BigDecimal.ONE)
                .setScale(SCORE_SCALE, RoundingMode.HALF_UP)
        }

        fun classBalanceScore(): BigDecimal? {
            val labeledRows = labelCounts.values.sum()
            if (labelIndex == null || labeledRows == 0 || labelCounts.size <= 1) {
                return null
            }
            val maxShare = labelCounts.values.max().toBigDecimal()
                .divide(labeledRows.toBigDecimal(), SCORE_SCALE, RoundingMode.HALF_UP)
            return (BigDecimal.ONE - maxShare)
                .multiply(BigDecimal("2"))
                .coerceIn(BigDecimal.ZERO, BigDecimal.ONE)
                .setScale(SCORE_SCALE, RoundingMode.HALF_UP)
        }

        private fun ratio(numerator: Int, denominator: Int): BigDecimal =
            if (denominator == 0) {
                BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP)
            } else {
                numerator.toBigDecimal().divide(denominator.toBigDecimal(), SCORE_SCALE, RoundingMode.HALF_UP)
            }
    }

    private fun BigDecimal.coerceIn(minimum: BigDecimal, maximum: BigDecimal): BigDecimal =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    private companion object {
        private const val SCORE_SCALE = 4
    }
}

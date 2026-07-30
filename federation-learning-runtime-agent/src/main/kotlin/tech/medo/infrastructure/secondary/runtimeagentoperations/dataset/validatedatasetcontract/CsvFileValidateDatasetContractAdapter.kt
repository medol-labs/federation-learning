package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.validatedatasetcontract

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractService
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Path

@Component
class CsvFileValidateDatasetContractAdapter(
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository
) : ValidateDatasetContractService {
    override fun execute(input: ValidateDatasetContractInput): ValidateDatasetContractResult {
        val binding = bindingRepository.findAll(Pageable.unpaged())
            .content
            .filter { it.datasetId == input.datasetId }
            .filter { it.isCsvFileBinding() }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }
            ?: return rejected("CSV runtime dataset binding was not found for dataset ${input.datasetId}.")

        val filePath = binding.filePath?.trim()
        if (filePath.isNullOrEmpty()) {
            return rejected("CSV runtime dataset binding filePath is required.")
        }

        val path = Path.of(filePath)
        if (!Files.isRegularFile(path)) {
            return rejected("CSV dataset file does not exist: $filePath")
        }
        if (!Files.isReadable(path)) {
            return rejected("CSV dataset file is not readable: $filePath")
        }

        val profile = readCsvProfile(path)
            ?: return rejected("CSV dataset file is empty: $filePath")

        if (profile.headerColumns.isEmpty()) {
            return rejected("CSV dataset file has no readable header: $filePath")
        }
        if (profile.sampleRowCount == 0) {
            return rejected("CSV dataset file has no readable sample row: $filePath")
        }

        val hasLabel = profile.headerColumns.any { it.normalized() in LABEL_COLUMNS }
        val qualityScore = profile.qualityScore()
        val nonIidScore = profile.nonIidScore()

        return if (hasLabel) {
            ValidateDatasetContractResult.Succeeded(
                schemaCompatible = true,
                labelCompatible = true,
                qualityScore = qualityScore,
                nonIidScore = nonIidScore
            )
        } else {
            ValidateDatasetContractResult.Rejected(
                schemaCompatible = true,
                labelCompatible = false,
                qualityScore = qualityScore,
                nonIidScore = nonIidScore,
                failureReason = "CSV dataset label column is required. Expected one of: ${LABEL_COLUMNS.joinToString(", ")}."
            )
        }
    }

    private fun rejected(reason: String): ValidateDatasetContractResult.Rejected =
        ValidateDatasetContractResult.Rejected(
            schemaCompatible = false,
            labelCompatible = false,
            qualityScore = BigDecimal.ZERO,
            nonIidScore = BigDecimal.ZERO,
            failureReason = reason
        )

    private fun RuntimeDatasetBindingCatalogReadModel.isCsvFileBinding(): Boolean {
        val source = dataSourceType.normalized()
        val format = dataFormat.normalized()
        return format == "csv" || source == "csv" || source == "file_csv" || (source == "file" && format == "csv")
    }

    private fun readCsvProfile(path: Path): CsvProfile? {
        Files.newBufferedReader(path).useLines { lines ->
            val iterator = lines.iterator()
            val header = iterator.nextNonBlankLine() ?: return null
            val headerColumns = header.toCsvColumns().filter { it.isNotBlank() }
            var sampleRowCount = 0
            var invalidRowCount = 0
            var missingCellCount = 0
            var totalCellCount = 0

            while (iterator.hasNext()) {
                val row = iterator.next().trim()
                if (row.isBlank()) {
                    continue
                }
                sampleRowCount += 1
                val cells = row.toCsvColumns()
                if (cells.size != headerColumns.size) {
                    invalidRowCount += 1
                }
                totalCellCount += headerColumns.size
                missingCellCount += cells.take(headerColumns.size).count { it.isBlank() }
                if (cells.size < headerColumns.size) {
                    missingCellCount += headerColumns.size - cells.size
                }
            }

            return CsvProfile(
                headerColumns = headerColumns,
                sampleRowCount = sampleRowCount,
                invalidRowCount = invalidRowCount,
                missingCellCount = missingCellCount,
                totalCellCount = totalCellCount
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

    private data class CsvProfile(
        val headerColumns: List<String>,
        val sampleRowCount: Int,
        val invalidRowCount: Int,
        val missingCellCount: Int,
        val totalCellCount: Int
    ) {
        fun qualityScore(): BigDecimal {
            if (sampleRowCount == 0 || totalCellCount == 0) {
                return BigDecimal.ZERO
            }
            val invalidPenalty = invalidRowCount.toBigDecimal()
                .divide(sampleRowCount.toBigDecimal(), SCORE_SCALE, RoundingMode.HALF_UP)
            val missingPenalty = missingCellCount.toBigDecimal()
                .divide(totalCellCount.toBigDecimal(), SCORE_SCALE, RoundingMode.HALF_UP)
            return (BigDecimal.ONE - invalidPenalty - missingPenalty)
                .coerceIn(BigDecimal.ZERO, BigDecimal.ONE)
                .setScale(SCORE_SCALE, RoundingMode.HALF_UP)
        }

        fun nonIidScore(): BigDecimal =
            BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP)
    }

    private fun BigDecimal.coerceIn(minimum: BigDecimal, maximum: BigDecimal): BigDecimal =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    private companion object {
        private const val SCORE_SCALE = 4
        private val LABEL_COLUMNS = setOf("label", "target", "y")
    }
}

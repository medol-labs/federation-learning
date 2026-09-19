package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.validatedatasetcontract

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.resolveRuntimeDatasetPath
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModel
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelRepository
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
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository,
    private val datasetCapabilityRepository: DatasetCapabilityReadModelRepository,
    @Value("\${runtime-agent.local-runtime-engine.dataset-host-root:../../volumes/datasets}")
    private val datasetHostRoot: String = "../../volumes/datasets",
    @Value("\${runtime-agent.local-runtime-engine.dataset-container-root:/workspace/datasets}")
    private val datasetContainerRoot: String = "/workspace/datasets"
) : ValidateDatasetContractService {
    override fun execute(input: ValidateDatasetContractInput): ValidateDatasetContractResult {
        val datasetCapability = datasetCapabilityRepository.findById(input.datasetId)
            ?: return rejected("Dataset feature schema snapshot was not found for dataset ${input.datasetId}.")
        if (datasetCapability.featureSchemaId != input.featureSchemaId) {
            return rejected(
                "Dataset feature schema mismatch. Expected ${input.featureSchemaId}, found ${datasetCapability.featureSchemaId}."
            )
        }
        if (datasetCapability.features.isEmpty()) {
            return rejected("Dataset feature schema snapshot has no features for dataset ${input.datasetId}.")
        }

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

        val path = resolveRuntimeDatasetPath(filePath, datasetHostRoot, datasetContainerRoot)
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

        val schemaResult = profile.validateSchema(datasetCapability)
        val qualityScore = profile.qualityScore()
        val nonIidScore = profile.nonIidScore()
        return if (schemaResult.schemaCompatible && schemaResult.labelCompatible) {
            ValidateDatasetContractResult.Succeeded(
                schemaCompatible = schemaResult.schemaCompatible,
                labelCompatible = schemaResult.labelCompatible,
                qualityScore = qualityScore,
                nonIidScore = nonIidScore
            )
        } else {
            ValidateDatasetContractResult.Rejected(
                schemaCompatible = schemaResult.schemaCompatible,
                labelCompatible = schemaResult.labelCompatible,
                qualityScore = qualityScore,
                nonIidScore = nonIidScore,
                failureReason = schemaResult.failureReason
            )
        }
    }

    private fun rejected(reason: String): ValidateDatasetContractResult.Rejected =
        ValidateDatasetContractResult.Rejected(
            schemaCompatible = false,
            labelCompatible = false,
            qualityScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            nonIidScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            failureReason = reason
        )

    private fun RuntimeDatasetBindingCatalogReadModel.isCsvFileBinding(): Boolean {
        val format = dataFormat.normalized()
        return format == "csv"
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
        normalize(this)

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

        fun validateSchema(datasetCapability: DatasetCapabilityReadModel): SchemaValidationResult {
            val actualColumns = headerColumns.map { normalize(it) }.toSet()
            val missingFeatures = datasetCapability.features
                .map { it.featureName }
                .filter { it.isNotBlank() }
                .filterNot { normalize(it) in actualColumns }
            val missingLabels = datasetCapability.labels
                .map { it.labelName }
                .filter { it.isNotBlank() }
                .filterNot { normalize(it) in actualColumns }
            val schemaCompatible = missingFeatures.isEmpty()
            val labelCompatible = missingLabels.isEmpty()
            return SchemaValidationResult(
                schemaCompatible = schemaCompatible,
                labelCompatible = labelCompatible,
                failureReason = buildList {
                    if (missingFeatures.isNotEmpty()) {
                        add("CSV dataset is missing feature columns: ${missingFeatures.joinToString(", ")}.")
                    }
                    if (missingLabels.isNotEmpty()) {
                        add("CSV dataset is missing label columns: ${missingLabels.joinToString(", ")}.")
                    }
                }.joinToString(" ")
            )
        }
    }

    private data class SchemaValidationResult(
        val schemaCompatible: Boolean,
        val labelCompatible: Boolean,
        val failureReason: String
    )

    private fun BigDecimal.coerceIn(minimum: BigDecimal, maximum: BigDecimal): BigDecimal =
        when {
            this < minimum -> minimum
            this > maximum -> maximum
            else -> this
        }

    private companion object {
        private const val SCORE_SCALE = 4

        private fun normalize(value: String?): String =
            value?.trim()?.lowercase()?.replace("-", "_") ?: ""
    }
}

package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.validatedatasetcontract

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.resolveRuntimeDatasetPath
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModel
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelRepository
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractService
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Path

@Component
class ImageFolderValidateDatasetContractAdapter(
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository,
    private val datasetCapabilityRepository: DatasetCapabilityReadModelRepository,
    @Value("\${runtime-agent.local-runtime-engine.dataset-host-root:../../volumes/datasets}")
    private val datasetHostRoot: String = "../../volumes/datasets",
    @Value("\${runtime-agent.local-runtime-engine.dataset-container-root:/workspace/datasets}")
    private val datasetContainerRoot: String = "/workspace/datasets"
) : ValidateDatasetContractService {
    override fun supports(input: ValidateDatasetContractInput): Boolean =
        findImageFolderBinding(input) != null

    override fun execute(input: ValidateDatasetContractInput): ValidateDatasetContractResult {
        val datasetCapability = datasetCapabilityRepository.findById(input.datasetId)
            ?: return rejected("Dataset feature schema snapshot was not found for dataset ${input.datasetId}.")
        if (datasetCapability.featureSchemaId != input.featureSchemaId) {
            return rejected(
                "Dataset feature schema mismatch. Expected ${input.featureSchemaId}, found ${datasetCapability.featureSchemaId}."
            )
        }

        val binding = findImageFolderBinding(input)
            ?: return rejected("ImageFolder runtime dataset binding was not found for dataset ${input.datasetId}.")
        val filePath = binding.filePath?.trim()
        if (filePath.isNullOrEmpty()) {
            return rejected("ImageFolder runtime dataset binding filePath is required.")
        }

        val path = resolveRuntimeDatasetPath(filePath, datasetHostRoot, datasetContainerRoot)
        if (!Files.isDirectory(path)) {
            return rejected("ImageFolder dataset directory does not exist: $filePath")
        }
        if (!Files.isReadable(path)) {
            return rejected("ImageFolder dataset directory is not readable: $filePath")
        }

        val trainProfile = profileSplit(path.resolve("train"))
            ?: return rejected("ImageFolder dataset train directory does not exist or has no readable class images: ${path.resolve("train")}")
        val schemaResult = trainProfile.validateSchema(datasetCapability)
        return if (schemaResult.schemaCompatible && schemaResult.labelCompatible) {
            ValidateDatasetContractResult.Succeeded(
                schemaCompatible = true,
                labelCompatible = true,
                qualityScore = BigDecimal.ONE.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
                nonIidScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP)
            )
        } else {
            ValidateDatasetContractResult.Rejected(
                schemaCompatible = schemaResult.schemaCompatible,
                labelCompatible = schemaResult.labelCompatible,
                qualityScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
                nonIidScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
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

    private fun findImageFolderBinding(input: ValidateDatasetContractInput): RuntimeDatasetBindingCatalogReadModel? =
        bindingRepository.findAllByCriteria(
            RuntimeDatasetBindingCatalogReadModelCriteria().apply {
                datasetId = StringFilter().apply { equals = input.datasetId.toString() }
            },
            PageRequest.of(0, 20)
        ).content
            .filter { it.isImageFolderBinding() }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }

    private fun RuntimeDatasetBindingCatalogReadModel.isImageFolderBinding(): Boolean =
        dataFormat.normalized() in setOf("image_folder", "imagefolder")

    private fun profileSplit(path: Path): ImageFolderSplitProfile? {
        if (!Files.isDirectory(path) || !Files.isReadable(path)) {
            return null
        }
        val classCounts = Files.list(path).use { stream ->
            stream.filter { Files.isDirectory(it) && Files.isReadable(it) }
                .map { classDirectory -> classDirectory.fileName.toString() to classDirectory.countImageFiles() }
                .filter { (_, count) -> count > 0 }
                .toList()
                .toMap()
        }
        if (classCounts.isEmpty()) {
            return null
        }
        return ImageFolderSplitProfile(classCounts)
    }

    private fun ImageFolderSplitProfile.validateSchema(
        datasetCapability: DatasetCapabilityReadModel
    ): ImageFolderSchemaValidationResult {
        val actualClasses = classCounts.keys.map { it.normalized() }.toSet()
        val expectedClasses = datasetCapability.labels
            .flatMap { it.classLabels }
            .filter { it.isNotBlank() }
        val missingClasses = expectedClasses.filterNot { it.normalized() in actualClasses }
        val labelCompatible = when {
            expectedClasses.isNotEmpty() -> missingClasses.isEmpty()
            else -> classCounts.size >= MIN_CLASS_COUNT
        }
        return ImageFolderSchemaValidationResult(
            schemaCompatible = datasetCapability.features.isNotEmpty(),
            labelCompatible = labelCompatible,
            failureReason = buildList {
                if (datasetCapability.features.isEmpty()) {
                    add("ImageFolder dataset feature schema snapshot has no features.")
                }
                if (expectedClasses.isNotEmpty() && missingClasses.isNotEmpty()) {
                    add("ImageFolder dataset is missing class directories: ${missingClasses.joinToString(", ")}.")
                }
                if (expectedClasses.isEmpty() && classCounts.size < MIN_CLASS_COUNT) {
                    add("ImageFolder dataset requires at least $MIN_CLASS_COUNT readable class directories.")
                }
            }.joinToString(" ")
        )
    }

    private fun Path.countImageFiles(): Int =
        Files.walk(this).use { stream ->
            stream.filter { Files.isRegularFile(it) && Files.isReadable(it) && it.isSupportedImageFile() }
                .count()
                .toInt()
        }

    private fun Path.isSupportedImageFile(): Boolean =
        fileName?.toString()
            ?.substringAfterLast('.', missingDelimiterValue = "")
            ?.lowercase() in SUPPORTED_IMAGE_EXTENSIONS

    private fun String?.normalized(): String =
        this?.trim()?.lowercase()?.replace("-", "_") ?: ""

    private data class ImageFolderSplitProfile(
        val classCounts: Map<String, Int>
    )

    private data class ImageFolderSchemaValidationResult(
        val schemaCompatible: Boolean,
        val labelCompatible: Boolean,
        val failureReason: String
    )

    private companion object {
        private const val MIN_CLASS_COUNT = 2
        private const val SCORE_SCALE = 4
        private val SUPPORTED_IMAGE_EXTENSIONS = setOf(
            "jpg",
            "jpeg",
            "png",
            "bmp",
            "gif",
            "webp",
            "tif",
            "tiff"
        )
    }
}

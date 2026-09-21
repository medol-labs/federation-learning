package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.profileagentdataset

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.resolveRuntimeDatasetPath
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetService
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Path

@Component
class ImageFolderProfileAgentDatasetAdapter(
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository,
    @Value("\${runtime-agent.local-runtime-engine.dataset-host-root:../../volumes/datasets}")
    private val datasetHostRoot: String = "../../volumes/datasets",
    @Value("\${runtime-agent.local-runtime-engine.dataset-container-root:/workspace/datasets}")
    private val datasetContainerRoot: String = "/workspace/datasets"
) : ProfileAgentDatasetService {
    override fun supports(input: ProfileAgentDatasetInput): Boolean =
        findImageFolderBinding(input) != null

    override fun execute(input: ProfileAgentDatasetInput): ProfileAgentDatasetResult {
        val binding = findImageFolderBinding(input)
            ?: return rejected("ImageFolder runtime dataset binding was not found for binding ${input.runtimeDatasetBindingId}.")

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

        val trainProfile = profileSplit(path.resolve("train"), "train")
            ?: return rejected("ImageFolder dataset train directory does not exist or has no readable class images: ${path.resolve("train")}")

        return ProfileAgentDatasetResult.Succeeded(
            sampleCount = trainProfile.sampleCount,
            featureCount = IMAGE_FOLDER_FEATURE_COUNT,
            schemaCompatible = true,
            labelCompatible = trainProfile.classCounts.size >= MIN_CLASS_COUNT,
            missingValueRate = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            duplicateRate = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            qualityScore = BigDecimal.ONE.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            nonIidScore = BigDecimal.ZERO.setScale(SCORE_SCALE, RoundingMode.HALF_UP),
            classBalanceScore = trainProfile.classBalanceScore()
        )
    }

    private fun rejected(reason: String): ProfileAgentDatasetResult.Rejected =
        ProfileAgentDatasetResult.Rejected(failureReason = reason)

    private fun findImageFolderBinding(input: ProfileAgentDatasetInput): RuntimeDatasetBindingCatalogReadModel? =
        bindingRepository.findAllByCriteria(
            RuntimeDatasetBindingCatalogReadModelCriteria().apply {
                runtimeDatasetBindingId = StringFilter().apply { equals = input.runtimeDatasetBindingId.toString() }
            },
            PageRequest.of(0, 20)
        ).content
            .ifEmpty {
                bindingRepository.findAllByCriteria(
                    RuntimeDatasetBindingCatalogReadModelCriteria().apply {
                        datasetId = StringFilter().apply { equals = input.datasetId.toString() }
                    },
                    PageRequest.of(0, 20)
                ).content
            }
            .filter { it.isImageFolderBinding() }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }

    private fun RuntimeDatasetBindingCatalogReadModel.isImageFolderBinding(): Boolean =
        dataFormat.normalized() in setOf("image_folder", "imagefolder")

    private fun profileSplit(path: Path, splitName: String): ImageFolderSplitProfile? {
        if (!Files.isDirectory(path) || !Files.isReadable(path)) {
            return null
        }

        val classCounts = Files.list(path).use { stream ->
            stream.filter { Files.isDirectory(it) && Files.isReadable(it) }
                .map { classDirectory ->
                    classDirectory.fileName.toString() to classDirectory.countImageFiles()
                }
                .filter { (_, count) -> count > 0 }
                .toList()
                .toMap()
        }
        if (classCounts.isEmpty()) {
            return null
        }

        return ImageFolderSplitProfile(
            splitName = splitName,
            classCounts = classCounts
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
        val splitName: String,
        val classCounts: Map<String, Int>
    ) {
        val sampleCount: Int = classCounts.values.sum()

        fun classBalanceScore(): BigDecimal? {
            val max = classCounts.values.maxOrNull() ?: return null
            val min = classCounts.values.minOrNull() ?: return null
            if (max <= 0) {
                return null
            }
            return BigDecimal(min).divide(BigDecimal(max), SCORE_SCALE, RoundingMode.HALF_UP)
        }
    }

    private companion object {
        private const val IMAGE_FOLDER_FEATURE_COUNT = 2
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

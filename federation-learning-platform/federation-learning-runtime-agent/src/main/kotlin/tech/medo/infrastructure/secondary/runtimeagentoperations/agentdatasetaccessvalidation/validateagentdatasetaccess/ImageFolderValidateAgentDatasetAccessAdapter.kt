package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetaccessvalidation.validateagentdatasetaccess

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.resolveRuntimeDatasetPath
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessService
import java.nio.file.Files
import java.nio.file.Path

@Component
class ImageFolderValidateAgentDatasetAccessAdapter(
    @Value("\${runtime-agent.local-runtime-engine.dataset-host-root:../../volumes/datasets}")
    private val datasetHostRoot: String = "../../volumes/datasets",
    @Value("\${runtime-agent.local-runtime-engine.dataset-container-root:/workspace/datasets}")
    private val datasetContainerRoot: String = "/workspace/datasets"
) : ValidateAgentDatasetAccessService {
    override fun supports(input: ValidateAgentDatasetAccessInput): Boolean =
        input.dataFormat.normalized() in setOf("image_folder", "imagefolder")

    override fun execute(input: ValidateAgentDatasetAccessInput): ValidateAgentDatasetAccessResult {
        fun rejected(reason: String): ValidateAgentDatasetAccessResult.Rejected =
            ValidateAgentDatasetAccessResult.Rejected(failureReason = reason)

        if (!supports(input)) {
            return rejected("Only ImageFolder dataset access validation is supported.")
        }

        val filePath = input.filePath.trim()
        if (filePath.isEmpty()) {
            return rejected("ImageFolder dataset binding filePath is required.")
        }

        val path = resolveRuntimeDatasetPath(filePath, datasetHostRoot, datasetContainerRoot)
        if (!Files.isDirectory(path)) {
            return rejected("ImageFolder dataset directory does not exist: $filePath")
        }
        if (!Files.isReadable(path)) {
            return rejected("ImageFolder dataset directory is not readable: $filePath")
        }

        val trainResult = validateSplit(path.resolve("train"), "train")
        if (trainResult != null) {
            return rejected(trainResult)
        }

        val valPath = path.resolve("val")
        if (Files.exists(valPath)) {
            val valResult = validateSplit(valPath, "val")
            if (valResult != null) {
                return rejected(valResult)
            }
        }

        return ValidateAgentDatasetAccessResult.Succeeded(
            readable = true,
            schemaReadable = true,
            sampleBatchReadable = true
        )
    }

    private fun validateSplit(path: Path, splitName: String): String? {
        if (!Files.isDirectory(path)) {
            return "ImageFolder dataset $splitName directory does not exist: $path"
        }
        if (!Files.isReadable(path)) {
            return "ImageFolder dataset $splitName directory is not readable: $path"
        }

        val classDirectories = Files.list(path).use { stream ->
            stream.filter { Files.isDirectory(it) && Files.isReadable(it) }
                .toList()
        }
        if (classDirectories.isEmpty()) {
            return "ImageFolder dataset $splitName directory has no readable class directories: $path"
        }

        val hasSampleFile = classDirectories.any { classDirectory ->
            Files.walk(classDirectory).use { stream ->
                stream.anyMatch { Files.isRegularFile(it) && Files.isReadable(it) && it.isSupportedImageFile() }
            }
        }
        if (!hasSampleFile) {
            return "ImageFolder dataset $splitName class directories have no readable sample files: $path"
        }

        return null
    }

    private fun Path.isSupportedImageFile(): Boolean =
        fileName?.toString()
            ?.substringAfterLast('.', missingDelimiterValue = "")
            ?.lowercase() in SUPPORTED_IMAGE_EXTENSIONS

    private fun String?.normalized(): String =
        this?.trim()?.lowercase()?.replace("-", "_") ?: ""

    private companion object {
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

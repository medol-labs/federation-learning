package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetaccessvalidation.validateagentdatasetaccess

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID

class ImageFolderValidateAgentDatasetAccessAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun validatesReadableImageFolderDataset() {
        createImageFolderDataset(tempDir)
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input(filePath = tempDir.toString()))

        assertTrue(result is ValidateAgentDatasetAccessResult.Succeeded)
        result as ValidateAgentDatasetAccessResult.Succeeded
        assertTrue(result.readable)
        assertTrue(result.schemaReadable)
        assertTrue(result.sampleBatchReadable)
    }

    @Test
    fun mapsContainerDatasetPathToHostDatasetRoot() {
        val hostRoot = tempDir.resolve("datasets")
        val dataset = hostRoot.resolve("tiny-imagenet")
        createImageFolderDataset(dataset)
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter(
            datasetHostRoot = hostRoot.toString(),
            datasetContainerRoot = "/workspace/datasets"
        )

        val result = adapter.execute(input(filePath = "/workspace/datasets/tiny-imagenet"))

        assertTrue(result is ValidateAgentDatasetAccessResult.Succeeded)
    }

    @Test
    fun validatesTinyImageNetNestedImagesDirectory() {
        Files.createDirectories(tempDir.resolve("train/n01443537/images"))
        Files.createDirectories(tempDir.resolve("train/n01629819/images"))
        Files.writeString(tempDir.resolve("train/n01443537/images/n01443537_0.JPEG"), "image")
        Files.writeString(tempDir.resolve("train/n01629819/images/n01629819_0.JPEG"), "image")
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input(filePath = tempDir.toString()))

        assertTrue(result is ValidateAgentDatasetAccessResult.Succeeded)
    }

    @Test
    fun rejectsMissingTrainDirectory() {
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input(filePath = tempDir.toString()))

        assertTrue(result is ValidateAgentDatasetAccessResult.Rejected)
        result as ValidateAgentDatasetAccessResult.Rejected
        assertTrue(result.failureReason.contains("train directory does not exist"))
    }

    @Test
    fun rejectsClassDirectoriesWithoutSamples() {
        Files.createDirectories(tempDir.resolve("train/n01443537"))
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input(filePath = tempDir.toString()))

        assertTrue(result is ValidateAgentDatasetAccessResult.Rejected)
        result as ValidateAgentDatasetAccessResult.Rejected
        assertTrue(result.failureReason.contains("no readable sample files"))
    }

    @Test
    fun supportsOnlyImageFolderFormats() {
        val adapter = ImageFolderValidateAgentDatasetAccessAdapter()

        assertTrue(adapter.supports(input(dataFormat = "IMAGE_FOLDER", filePath = tempDir.toString())))
        assertTrue(adapter.supports(input(dataFormat = "image-folder", filePath = tempDir.toString())))
        assertFalse(adapter.supports(input(dataFormat = "csv", filePath = tempDir.toString())))
    }

    private fun createImageFolderDataset(root: Path) {
        Files.createDirectories(root.resolve("train/n01443537"))
        Files.createDirectories(root.resolve("train/n01629819"))
        Files.createDirectories(root.resolve("val/n01443537"))
        Files.writeString(root.resolve("train/n01443537/sample.JPEG"), "image")
        Files.writeString(root.resolve("train/n01629819/sample.JPEG"), "image")
        Files.writeString(root.resolve("val/n01443537/sample.JPEG"), "image")
    }

    private fun input(
        dataFormat: String = "IMAGE_FOLDER",
        filePath: String
    ): ValidateAgentDatasetAccessInput =
        ValidateAgentDatasetAccessInput(
            datasetAccessValidationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            organizationName = "Test Organization",
            featureSchemaId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            featureDomain = "tiny-imagenet",
            featureSchemaVersion = "v1",
            datasetName = "tiny-imagenet",
            runtimeId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeName = "local runtime",
            filePath = filePath,
            dataFormat = dataFormat
        )
}

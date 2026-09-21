package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.profileagentdataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.UUID

class ImageFolderProfileAgentDatasetAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun profilesTinyImageNetNestedImagesDirectory() {
        Files.createDirectories(tempDir.resolve("train/n01443537/images"))
        Files.createDirectories(tempDir.resolve("train/n01629819/images"))
        Files.writeString(tempDir.resolve("train/n01443537/images/n01443537_0.JPEG"), "image")
        Files.writeString(tempDir.resolve("train/n01443537/images/n01443537_1.JPEG"), "image")
        Files.writeString(tempDir.resolve("train/n01629819/images/n01629819_0.JPEG"), "image")
        val adapter = ImageFolderProfileAgentDatasetAdapter(
            repository(binding(filePath = tempDir.toString()))
        )

        val result = adapter.execute(input())

        assertTrue(result is ProfileAgentDatasetResult.Succeeded)
        result as ProfileAgentDatasetResult.Succeeded
        assertEquals(3, result.sampleCount)
        assertEquals(2, result.featureCount)
        assertEquals(true, result.schemaCompatible)
        assertEquals(true, result.labelCompatible)
        assertEquals(BigDecimal("0.5000"), result.classBalanceScore)
    }

    @Test
    fun supportsOnlyImageFolderBindings() {
        val imageFolderAdapter = ImageFolderProfileAgentDatasetAdapter(
            repository(binding(filePath = tempDir.toString(), dataFormat = "IMAGE_FOLDER"))
        )
        val csvAdapter = ImageFolderProfileAgentDatasetAdapter(
            repository(binding(filePath = tempDir.toString(), dataFormat = "csv"))
        )

        assertTrue(imageFolderAdapter.supports(input()))
        assertFalse(csvAdapter.supports(input()))
    }

    private fun input(): ProfileAgentDatasetInput =
        ProfileAgentDatasetInput(
            metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = BINDING_ID,
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            organizationName = "Test Organization",
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            featureDomain = "tiny-imagenet",
            featureSchemaVersion = "v1",
            datasetName = "tiny-imagenet",
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            runtimeName = "local runtime"
        )

    private fun binding(
        filePath: String,
        dataFormat: String = "IMAGE_FOLDER"
    ): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = BINDING_ID,
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            organizationName = "Test Organization",
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            featureDomain = "tiny-imagenet",
            featureSchemaVersion = "v1",
            datasetName = "tiny-imagenet",
            runtimeName = "local runtime",
            filePath = filePath,
            dataFormat = dataFormat,
            configuredAt = LocalDateTime.now(),
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )

    private fun repository(vararg bindings: RuntimeDatasetBindingCatalogReadModel): RuntimeDatasetBindingCatalogReadModelRepository =
        object : RuntimeDatasetBindingCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
                PageImpl(bindings.toList())

            override fun findAllByCriteria(
                criteria: RuntimeDatasetBindingCatalogReadModelCriteria?,
                pageable: Pageable
            ): Page<RuntimeDatasetBindingCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? =
                bindings.firstOrNull { it.runtimeDatasetBindingId == id }

            override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? =
                null

            override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) {
                error("save is not used by this test repository")
            }
        }

    private companion object {
        val BINDING_ID: UUID = UUID.fromString("22222222-2222-4222-8222-222222222222")
        val DATASET_ID: UUID = UUID.fromString("33333333-3333-4333-8333-333333333333")
    }
}

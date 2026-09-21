package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.validatedatasetcontract

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModel
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelCriteria
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelProjection
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelRepository
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.UUID

class ImageFolderValidateDatasetContractAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun validatesImageFolderDatasetContract() {
        createImageFolderDataset(tempDir)
        val adapter = ImageFolderValidateDatasetContractAdapter(
            repository(binding(filePath = tempDir.toString())),
            datasetCapabilityRepository(datasetCapability())
        )

        val result = adapter.execute(input())

        assertTrue(result is ValidateDatasetContractResult.Succeeded)
    }

    @Test
    fun rejectsImageFolderWhenExpectedClassIsMissing() {
        Files.createDirectories(tempDir.resolve("train/n01443537/images"))
        Files.writeString(tempDir.resolve("train/n01443537/images/sample.JPEG"), "image")
        val adapter = ImageFolderValidateDatasetContractAdapter(
            repository(binding(filePath = tempDir.toString())),
            datasetCapabilityRepository(datasetCapability())
        )

        val result = adapter.execute(input())

        assertTrue(result is ValidateDatasetContractResult.Rejected)
        result as ValidateDatasetContractResult.Rejected
        assertTrue(result.failureReason.contains("missing class directories"))
    }

    @Test
    fun supportsOnlyImageFolderBindings() {
        val imageFolderAdapter = ImageFolderValidateDatasetContractAdapter(
            repository(binding(filePath = tempDir.toString(), dataFormat = "IMAGE_FOLDER")),
            datasetCapabilityRepository(datasetCapability())
        )
        val csvAdapter = ImageFolderValidateDatasetContractAdapter(
            repository(binding(filePath = tempDir.toString(), dataFormat = "csv")),
            datasetCapabilityRepository(datasetCapability())
        )

        assertTrue(imageFolderAdapter.supports(input()))
        assertFalse(csvAdapter.supports(input()))
    }

    private fun createImageFolderDataset(root: Path) {
        Files.createDirectories(root.resolve("train/n01443537/images"))
        Files.createDirectories(root.resolve("train/n01629819/images"))
        Files.writeString(root.resolve("train/n01443537/images/n01443537_0.JPEG"), "image")
        Files.writeString(root.resolve("train/n01629819/images/n01629819_0.JPEG"), "image")
    }

    private fun input(): ValidateDatasetContractInput =
        ValidateDatasetContractInput(
            datasetId = DATASET_ID,
            metadataReportId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = FEATURE_SCHEMA_ID
        )

    private fun binding(
        filePath: String,
        dataFormat: String = "IMAGE_FOLDER"
    ): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            organizationName = "Test Organization",
            featureSchemaId = FEATURE_SCHEMA_ID,
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

    private fun datasetCapabilityRepository(
        datasetCapability: DatasetCapabilityReadModel? = null
    ): DatasetCapabilityReadModelRepository =
        object : DatasetCapabilityReadModelRepository {
            override fun findAll(pageable: Pageable): Page<DatasetCapabilityReadModel> =
                PageImpl(listOfNotNull(datasetCapability))

            override fun findAllByCriteria(
                criteria: DatasetCapabilityReadModelCriteria?,
                pageable: Pageable
            ): Page<DatasetCapabilityReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): DatasetCapabilityReadModel? =
                datasetCapability?.takeIf { it.datasetId == id }

            override fun findProjectionById(id: UUID): DatasetCapabilityReadModelProjection? =
                null

            override fun save(projection: DatasetCapabilityReadModelProjection) {
                error("save is not used by this test repository")
            }
        }

    private fun datasetCapability(): DatasetCapabilityReadModel =
        DatasetCapabilityReadModel(
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            featureSchemaId = FEATURE_SCHEMA_ID,
            features = listOf(
                FeatureDefinition(
                    featureName = "image",
                    dataType = "FILE",
                    required = true,
                    nullable = false,
                    description = null,
                    validationRules = emptyList(),
                    defaultValue = null,
                    isIdentifier = false,
                    isSensitive = false,
                    encodingStrategy = null,
                    featureTags = emptyList()
                )
            ),
            labels = listOf(
                LabelDefinition(
                    labelName = "class",
                    dataType = "STRING",
                    cardinality = 2,
                    classLabels = listOf("n01443537", "n01629819"),
                    isMultilabel = false,
                    description = null,
                    validationRules = emptyList(),
                    defaultValue = null
                )
            ),
            organizationName = null,
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "tiny-imagenet",
            datasetUsage = "TRAINING",
            sampleCount = null,
            featureCount = null,
            schemaCompatible = null,
            labelCompatible = null,
            qualityScore = null,
            nonIidScore = null,
            metadataReportId = null,
            metadataStatus = null,
            contractStatus = null,
            approvalStatus = null,
            approved = null,
            lastProfiledAt = null,
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )

    private companion object {
        val DATASET_ID: UUID = UUID.fromString("11111111-1111-4111-8111-111111111111")
        val FEATURE_SCHEMA_ID: UUID = UUID.fromString("33333333-3333-4333-8333-333333333333")
    }
}

package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.validatedatasetcontract

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractInput
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID

class CsvFileValidateDatasetContractAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun validatesCsvDatasetContract() {
        val csv = tempDir.resolve("dataset.csv")
        Files.writeString(csv, "age,income,label\n42,1000,yes\n36,900,no\n")
        val adapter = CsvFileValidateDatasetContractAdapter(repository(binding(filePath = csv.toString())))

        val result = adapter.execute(input())

        assertTrue(result is ValidateDatasetContractResult.Succeeded)
        result as ValidateDatasetContractResult.Succeeded
        assertTrue(result.schemaCompatible)
        assertTrue(result.labelCompatible)
        assertEquals(BigDecimal("1.0000"), result.qualityScore)
        assertEquals(BigDecimal("0.0000"), result.nonIidScore)
    }

    @Test
    fun rejectsCsvWithoutLabelColumn() {
        val csv = tempDir.resolve("dataset.csv")
        Files.writeString(csv, "age,income\n42,1000\n")
        val adapter = CsvFileValidateDatasetContractAdapter(repository(binding(filePath = csv.toString())))

        val result = adapter.execute(input())

        assertTrue(result is ValidateDatasetContractResult.Rejected)
        result as ValidateDatasetContractResult.Rejected
        assertTrue(result.schemaCompatible)
        assertEquals(false, result.labelCompatible)
        assertTrue(result.failureReason.contains("label column"))
    }

    @Test
    fun rejectsWhenCsvBindingCannotBeFound() {
        val adapter = CsvFileValidateDatasetContractAdapter(repository())

        val result = adapter.execute(input())

        assertTrue(result is ValidateDatasetContractResult.Rejected)
        result as ValidateDatasetContractResult.Rejected
        assertTrue(result.failureReason.contains("binding was not found"))
    }

    private fun input(): ValidateDatasetContractInput =
        ValidateDatasetContractInput(
            datasetId = DATASET_ID,
            metadataReportId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = UUID.fromString("33333333-3333-4333-8333-333333333333")
        )

    private fun binding(filePath: String): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            datasetName = "credit-risk",
            dataSourceType = "file",
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = filePath,
            objectBucket = null,
            objectPrefix = null,
            dataFormat = "csv",
            credentialSecretName = null,
            configuredAt = java.time.LocalDateTime.now(),
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

            override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? =
                bindings.firstOrNull { it.runtimeDatasetBindingId == id }

            override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? =
                null

            override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) {
                error("save is not used by this test repository")
            }
        }

    private companion object {
        val DATASET_ID: UUID = UUID.fromString("11111111-1111-4111-8111-111111111111")
    }
}

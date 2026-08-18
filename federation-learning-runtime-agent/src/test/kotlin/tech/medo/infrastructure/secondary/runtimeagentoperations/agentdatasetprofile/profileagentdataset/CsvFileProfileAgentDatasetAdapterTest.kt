package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetprofile.profileagentdataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.CsvDatasetProperties
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetInput
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetResult
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.UUID

class CsvFileProfileAgentDatasetAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun profilesReadableCsvFile() {
        val csv = tempDir.resolve("dataset.csv")
        Files.writeString(csv, "age,income,label\n42,1000,yes\n36,900,no\n42,1000,yes\n")
        val adapter = CsvFileProfileAgentDatasetAdapter(
            repository(binding(filePath = csv.toString())),
            csvDatasetProperties()
        )

        val result = adapter.execute(input())

        assertTrue(result is ProfileAgentDatasetResult.Succeeded)
        result as ProfileAgentDatasetResult.Succeeded
        assertEquals(3, result.sampleCount)
        assertEquals(3, result.featureCount)
        assertEquals(true, result.schemaCompatible)
        assertEquals(true, result.labelCompatible)
        assertEquals(BigDecimal("0.0000"), result.missingValueRate)
        assertEquals(BigDecimal("0.3333"), result.duplicateRate)
        assertEquals(BigDecimal("0.6667"), result.qualityScore)
        assertEquals(BigDecimal("0.6666"), result.classBalanceScore)
    }

    @Test
    fun profilesCsvWithoutLabelAsLabelIncompatible() {
        val csv = tempDir.resolve("dataset.csv")
        Files.writeString(csv, "age,income\n42,1000\n36,\n")
        val adapter = CsvFileProfileAgentDatasetAdapter(
            repository(binding(filePath = csv.toString())),
            csvDatasetProperties()
        )

        val result = adapter.execute(input())

        assertTrue(result is ProfileAgentDatasetResult.Succeeded)
        result as ProfileAgentDatasetResult.Succeeded
        assertEquals(false, result.labelCompatible)
        assertEquals(BigDecimal("0.2500"), result.missingValueRate)
        assertEquals(null, result.classBalanceScore)
    }

    @Test
    fun rejectsWhenCsvBindingCannotBeFound() {
        val adapter = CsvFileProfileAgentDatasetAdapter(repository(), csvDatasetProperties())

        val result = adapter.execute(input())

        assertTrue(result is ProfileAgentDatasetResult.Rejected)
        result as ProfileAgentDatasetResult.Rejected
        assertTrue(result.failureReason.contains("binding"))
    }

    private fun input(): ProfileAgentDatasetInput =
        ProfileAgentDatasetInput(
            metadataReportId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = BINDING_ID,
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            featureSchemaId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            datasetName = "credit-risk",
            runtimeId = UUID.fromString("66666666-6666-4666-8666-666666666666")
        )

    private fun binding(filePath: String): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = BINDING_ID,
            datasetId = DATASET_ID,
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
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

            override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? =
                bindings.firstOrNull { it.runtimeDatasetBindingId == id }

            override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? =
                null

            override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) {
                error("save is not used by this test repository")
            }
        }

    private fun csvDatasetProperties(): CsvDatasetProperties =
        CsvDatasetProperties(labelColumns = listOf("label"))

    private companion object {
        val BINDING_ID: UUID = UUID.fromString("22222222-2222-4222-8222-222222222222")
        val DATASET_ID: UUID = UUID.fromString("33333333-3333-4333-8333-333333333333")
    }
}

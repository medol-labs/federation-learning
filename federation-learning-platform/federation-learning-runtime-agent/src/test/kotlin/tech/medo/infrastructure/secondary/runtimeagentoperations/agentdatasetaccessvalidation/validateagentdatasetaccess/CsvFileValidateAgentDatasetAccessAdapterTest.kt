package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetaccessvalidation.validateagentdatasetaccess

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID

class CsvFileValidateAgentDatasetAccessAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun validatesReadableCsvFile() {
        val csv = tempDir.resolve("dataset.csv")
        Files.writeString(csv, "age,label\n42,yes\n")
        val input = input(filePath = csv.toString())
        val adapter = CsvFileValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input)

        assertTrue(result is ValidateAgentDatasetAccessResult.Succeeded)
        result as ValidateAgentDatasetAccessResult.Succeeded
        assertTrue(result.readable)
        assertTrue(result.schemaReadable)
        assertTrue(result.sampleBatchReadable)
    }

    @Test
    fun mapsContainerDatasetPathToHostDatasetRoot() {
        val hostRoot = tempDir.resolve("datasets")
        Files.createDirectories(hostRoot)
        Files.writeString(hostRoot.resolve("dataset.csv"), "age,label\n42,yes\n")
        val input = input(filePath = "/workspace/datasets/dataset.csv")
        val adapter = CsvFileValidateAgentDatasetAccessAdapter(
            datasetHostRoot = hostRoot.toString(),
            datasetContainerRoot = "/workspace/datasets"
        )

        val result = adapter.execute(input)

        assertTrue(result is ValidateAgentDatasetAccessResult.Succeeded)
    }

    @Test
    fun rejectsMissingCsvFile() {
        val input = input(filePath = tempDir.resolve("missing.csv").toString())
        val adapter = CsvFileValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input)

        assertTrue(result is ValidateAgentDatasetAccessResult.Rejected)
        result as ValidateAgentDatasetAccessResult.Rejected
        assertTrue(result.failureReason.contains("does not exist"))
    }

    @Test
    fun rejectsNonCsvBinding() {
        val input = input(dataFormat = "table", filePath = tempDir.resolve("dataset.table").toString())
        val adapter = CsvFileValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input)

        assertTrue(result is ValidateAgentDatasetAccessResult.Rejected)
        result as ValidateAgentDatasetAccessResult.Rejected
        assertEquals("Only CSV file dataset access validation is supported.", result.failureReason)
    }

    private fun input(
        dataFormat: String = "csv",
        filePath: String
    ): ValidateAgentDatasetAccessInput =
        ValidateAgentDatasetAccessInput(
            datasetAccessValidationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            organizationId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            organizationName = "Test Organization",
            featureSchemaId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            featureDomain = "credit-risk",
            featureSchemaVersion = "v1",
            datasetName = "credit-risk",
            runtimeId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            runtimeName = "local runtime",
            filePath = filePath,
            dataFormat = dataFormat
        )
}

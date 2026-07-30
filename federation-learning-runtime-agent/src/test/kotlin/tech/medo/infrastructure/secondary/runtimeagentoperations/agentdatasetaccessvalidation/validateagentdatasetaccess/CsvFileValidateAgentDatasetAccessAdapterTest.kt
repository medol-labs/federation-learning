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
        val input = input(dataSourceType = "postgres", dataFormat = "table", filePath = null)
        val adapter = CsvFileValidateAgentDatasetAccessAdapter()

        val result = adapter.execute(input)

        assertTrue(result is ValidateAgentDatasetAccessResult.Rejected)
        result as ValidateAgentDatasetAccessResult.Rejected
        assertEquals("Only CSV file dataset access validation is supported.", result.failureReason)
    }

    private fun input(
        dataSourceType: String = "file",
        dataFormat: String = "csv",
        filePath: String?
    ): ValidateAgentDatasetAccessInput =
        ValidateAgentDatasetAccessInput(
            datasetAccessValidationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeDatasetBindingId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            datasetId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            runtimeId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            dataSourceType = dataSourceType,
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = filePath,
            objectBucket = null,
            objectPrefix = null,
            dataFormat = dataFormat,
            credentialSecretName = null
        )
}

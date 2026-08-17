package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

class LocalDockerComposeStartRoundExecutionAdapterTest {
    private val runtimeId = UUID.fromString("11111111-1111-4111-8111-111111111111")
    private val organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222")

    @Test
    fun startsRuntimeEngineAndSubmitsTrainingJob() {
        val runner = RecordingRunner(LocalRuntimeEngineCommandResult(exitCode = 0, output = "started", timedOut = false))
        val client = RecordingClient()
        val adapter = adapter(
            runner = runner,
            client = client,
            bindings = listOf(binding(filePath = "../volumes/datasets/alice.csv"))
        )

        val result = adapter.execute(input(runtimeEngineJobId = "job-1"))

        assertTrue(result is StartRoundExecutionResult.Succeeded)
        assertEquals(listOf(listOf("up", "-d", "runtime-engine")), runner.commands)
        assertEquals("http://localhost:18080", client.healthEndpoints.single())
        val request = client.jobs.single()
        assertEquals("job-1", request.jobId)
        assertEquals("train", request.operation)
        assertEquals("local-runtime", request.myName)
        assertEquals("/workspace/datasets/alice.csv", (request.input["dataset"] as Map<*, *>)["path"])
        assertEquals("/workspace/tmp/runtime-engine/job-1/local-runtime/local_update.json", request.output["local_update"])
    }

    @Test
    fun rejectsWhenDatasetBindingIsMissing() {
        val adapter = adapter(bindings = emptyList())

        val result = adapter.execute(input())

        assertTrue(result is StartRoundExecutionResult.Rejected)
        result as StartRoundExecutionResult.Rejected
        assertTrue(result.failureReason.contains("No runtime dataset binding"))
    }

    @Test
    fun reportsUnavailableWhenDockerComposeFails() {
        val adapter = adapter(
            runner = RecordingRunner(LocalRuntimeEngineCommandResult(exitCode = 1, output = "compose failed", timedOut = false)),
            bindings = listOf(binding(filePath = "../volumes/datasets/alice.csv"))
        )

        val result = adapter.execute(input())

        assertTrue(result is StartRoundExecutionResult.Unavailable)
        result as StartRoundExecutionResult.Unavailable
        assertTrue(result.failureReason.contains("docker compose up failed"))
    }

    @Test
    fun reportsUnavailableWhenRuntimeEngineHealthCheckFails() {
        val adapter = adapter(
            client = RecordingClient(healthException = IllegalStateException("connection refused")),
            bindings = listOf(binding(filePath = "../volumes/datasets/alice.csv"))
        )

        val result = adapter.execute(input())

        assertTrue(result is StartRoundExecutionResult.Unavailable)
        result as StartRoundExecutionResult.Unavailable
        assertTrue(result.failureReason.contains("health check failed"))
    }

    @Test
    fun reportsUnavailableWhenRuntimeEngineJobSubmissionFails() {
        val adapter = adapter(
            client = RecordingClient(startException = IllegalStateException("boom")),
            bindings = listOf(binding(filePath = "../volumes/datasets/alice.csv"))
        )

        val result = adapter.execute(input())

        assertTrue(result is StartRoundExecutionResult.Unavailable)
        result as StartRoundExecutionResult.Unavailable
        assertTrue(result.failureReason.contains("job submission failed"))
    }

    private fun adapter(
        runner: RecordingRunner = RecordingRunner(LocalRuntimeEngineCommandResult(exitCode = 0, output = "started", timedOut = false)),
        client: RecordingClient = RecordingClient(),
        bindings: List<RuntimeDatasetBindingCatalogReadModel>
    ): LocalDockerComposeStartRoundExecutionAdapter =
        LocalDockerComposeStartRoundExecutionAdapter(
            properties = LocalRuntimeEngineProperties(
                composeFile = "../federation-learning-runtime-engine/docker-compose.yml",
                projectName = "runtime-engine",
                serviceName = "runtime-engine",
                endpoint = "http://localhost:18080",
                nodeName = "local-runtime",
                datasetHostRoot = "../volumes/datasets",
                datasetContainerRoot = "/workspace/datasets",
                healthTimeout = Duration.ofMillis(250),
                healthPollInterval = Duration.ofMillis(10)
            ),
            commandRunner = runner,
            runtimeEngineClient = client,
            bindingRepository = bindingRepository(bindings)
        )

    private fun input(runtimeEngineJobId: String = ""): StartRoundExecutionInput =
        StartRoundExecutionInput(
            roundExecutionId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            executionSessionId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            executionPlanId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            trainingJobId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            trainingRunConfigurationId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            roundId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            roundNumber = 1,
            runtimeId = runtimeId,
            organizationId = organizationId,
            featureSchemaId = UUID.fromString("99999999-9999-4999-8999-999999999999"),
            baseModelId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            baseModelArtifactUri = "file:///workspace/inputs/global_model.json",
            baseModelRegistryRef = "local",
            baseModelFormat = "json",
            baseModelArtifactDigest = "sha256:abc",
            baseModelSignatureUri = null,
            runtimeEngineJobId = runtimeEngineJobId
        )

    private fun binding(filePath: String): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = UUID.fromString("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"),
            datasetId = UUID.fromString("cccccccc-cccc-4ccc-8ccc-cccccccccccc"),
            organizationId = organizationId,
            runtimeId = runtimeId,
            datasetName = "alice",
            dataSourceType = "FILE",
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = filePath,
            objectBucket = null,
            objectPrefix = null,
            dataFormat = "CSV",
            credentialSecretName = null,
            configuredAt = LocalDateTime.now(),
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )

    private fun bindingRepository(
        bindings: List<RuntimeDatasetBindingCatalogReadModel>
    ): RuntimeDatasetBindingCatalogReadModelRepository =
        object : RuntimeDatasetBindingCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
                PageImpl(bindings)

            override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? = null
            override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? = null
            override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) = Unit
        }

    private class RecordingRunner(
        private val result: LocalRuntimeEngineCommandResult
    ) : LocalRuntimeEngineCommandRunner {
        val commands = mutableListOf<List<String>>()

        override fun run(properties: LocalRuntimeEngineProperties, arguments: List<String>): LocalRuntimeEngineCommandResult {
            commands += arguments
            return result
        }
    }

    private class RecordingClient(
        private val healthException: RuntimeException? = null,
        private val startException: RuntimeException? = null
    ) : RuntimeEngineClient {
        val healthEndpoints = mutableListOf<String>()
        val jobs = mutableListOf<RuntimeEngineJobRequest>()

        override fun health(endpoint: String): RuntimeEngineHealthResponse {
            healthException?.let { throw it }
            healthEndpoints += endpoint
            return RuntimeEngineHealthResponse(status = "ok", nodeName = "Alice")
        }

        override fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse {
            startException?.let { throw it }
            jobs += request
            return RuntimeEngineJobResponse(jobId = request.jobId, nodeName = request.myName, status = "running")
        }
    }
}

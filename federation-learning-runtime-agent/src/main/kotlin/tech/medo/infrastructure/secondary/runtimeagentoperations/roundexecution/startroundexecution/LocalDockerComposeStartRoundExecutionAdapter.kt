package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionService
import java.nio.file.Path
import java.time.Duration
import java.time.Instant

@Component
class LocalDockerComposeStartRoundExecutionAdapter(
    private val properties: LocalRuntimeEngineProperties,
    private val commandRunner: LocalRuntimeEngineCommandRunner,
    private val runtimeEngineClient: GemiFlRuntimeEngineClient,
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository
) : StartRoundExecutionService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: StartRoundExecutionInput): Boolean = properties.enabled

    override fun execute(input: StartRoundExecutionInput): StartRoundExecutionResult {
        val binding = findBinding(input)
            ?: return StartRoundExecutionResult.Rejected(
                failureReason = "No runtime dataset binding is available for runtime ${input.runtimeId}."
            )
        val datasetPath = binding.filePath?.takeIf { it.isNotBlank() }
            ?: return StartRoundExecutionResult.Rejected(
                failureReason = "Runtime dataset binding ${binding.runtimeDatasetBindingId} does not provide a filePath."
            )

        val compose = commandRunner.run(properties, listOf("up", "-d", properties.serviceName))
        if (!compose.succeeded) {
            return StartRoundExecutionResult.Unavailable(
                failureReason = if (compose.timedOut) {
                    "GemiFL docker compose up timed out after ${properties.commandTimeout}."
                } else {
                    "GemiFL docker compose up failed with exitCode=${compose.exitCode}: ${compose.output}"
                }
            )
        }

        val endpoint = properties.endpoint.trim().removeSuffix("/")
        waitUntilHealthy(endpoint)

        val runtimeEngineJobId = input.runtimeEngineJobId.ifBlank { defaultRuntimeEngineJobId(input) }
        val request = buildJobRequest(input, runtimeEngineJobId, datasetPath)
        val response = runtimeEngineClient.startJob(endpoint, request)

        log.info(
            "Started local GemiFL round execution. roundExecutionId={}, executionPlanId={}, runtimeEngineJobId={}, endpoint={}, status={}, output={}",
            input.roundExecutionId,
            input.executionPlanId,
            runtimeEngineJobId,
            endpoint,
            response.status,
            response.output
        )
        return StartRoundExecutionResult.Succeeded()
    }

    private fun findBinding(input: StartRoundExecutionInput): RuntimeDatasetBindingCatalogReadModel? =
        bindingRepository.findAll(Pageable.unpaged()).content
            .filter { it.runtimeId == input.runtimeId }
            .filter { it.organizationId == input.organizationId }
            .filter { it.datasetId != null }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }

    private fun waitUntilHealthy(endpoint: String) {
        val deadline = Instant.now().plus(properties.healthTimeout.coerceAtLeast(Duration.ofSeconds(1)))
        var lastFailure: Exception? = null
        while (Instant.now().isBefore(deadline)) {
            try {
                val health = runtimeEngineClient.health(endpoint)
                if (health.status.equals("ok", ignoreCase = true)) {
                    return
                }
            } catch (ex: Exception) {
                lastFailure = ex
            }
            Thread.sleep(properties.healthPollInterval.coerceAtLeast(Duration.ofMillis(100)).toMillis())
        }
        throw IllegalStateException(
            "GemiFL runtime engine at $endpoint did not become healthy within ${properties.healthTimeout}." +
                (lastFailure?.message?.let { " Last error: $it" } ?: "")
        )
    }

    private fun buildJobRequest(
        input: StartRoundExecutionInput,
        runtimeEngineJobId: String,
        datasetPath: String
    ): GemiFlJobRequest {
        val localUpdatePath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/local_update.json"
        val metricsPath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/metrics.json"
        val globalModelPath = input.baseModelArtifactUri
            .takeIf { it.startsWith("/") || it.startsWith("file://") }
            ?.removePrefix("file://")

        val runtimeInput = mutableMapOf<String, Any?>(
            "dataset" to mapOf(
                "path" to toContainerDatasetPath(datasetPath),
                "label_column" to "y",
                "id" to "id"
            )
        )
        if (!globalModelPath.isNullOrBlank()) {
            runtimeInput["global_model"] = globalModelPath
        }

        return GemiFlJobRequest(
            jobId = runtimeEngineJobId,
            taskId = runtimeEngineJobId,
            roundId = input.roundNumber,
            myName = properties.nodeName,
            role = "trainer",
            operation = "train",
            input = runtimeInput,
            output = mapOf(
                "local_update" to localUpdatePath,
                "metrics" to metricsPath
            ),
            modelParameter = mapOf(
                "model" to properties.model,
                "engine" to "python",
                "process" to "train",
                "epoch" to properties.epoch,
                "learning_rate" to properties.learningRate
            ),
            jobParameter = mapOf(
                "encrypt_method" to "plain",
                "logger_level" to "INFO"
            ),
            runtimeRoot = properties.runtimeRoot
        )
    }

    private fun toContainerDatasetPath(datasetPath: String): String {
        val hostRoot = properties.datasetHostRoot.takeIf { it.isNotBlank() } ?: return datasetPath
        val containerRoot = properties.datasetContainerRoot.takeIf { it.isNotBlank() } ?: return datasetPath
        val normalizedDatasetPath = Path.of(datasetPath).toAbsolutePath().normalize().toString()
        val normalizedHostRoot = Path.of(hostRoot).toAbsolutePath().normalize().toString()
        return if (normalizedDatasetPath == normalizedHostRoot || normalizedDatasetPath.startsWith("$normalizedHostRoot/")) {
            containerRoot.trimEnd('/') + normalizedDatasetPath.removePrefix(normalizedHostRoot)
        } else {
            datasetPath
        }
    }

    private fun defaultRuntimeEngineJobId(input: StartRoundExecutionInput): String =
        "round-${input.roundNumber}-${input.executionPlanId}"
}

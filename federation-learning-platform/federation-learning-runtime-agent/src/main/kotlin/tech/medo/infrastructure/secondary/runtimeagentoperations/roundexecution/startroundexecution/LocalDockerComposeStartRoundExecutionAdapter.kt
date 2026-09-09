package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
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
    private val runtimeEngineClient: RuntimeEngineClient,
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository
) : StartRoundExecutionService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: StartRoundExecutionInput): Boolean = properties.enabled && properties.usesDockerCompose()

    override fun execute(input: StartRoundExecutionInput): StartRoundExecutionResult {
        val binding = findBinding(input)
            ?: return StartRoundExecutionResult.Rejected(
                runtimeEngineJobId = null,
                failureReason = "No runtime dataset binding is available for runtime ${input.runtimeId}."
            )
        val datasetPath = binding.filePath?.takeIf { it.isNotBlank() }
            ?: return StartRoundExecutionResult.Rejected(
                runtimeEngineJobId = null,
                failureReason = "Runtime dataset binding ${binding.runtimeDatasetBindingId} does not provide a filePath."
            )
        val runtimeEngineJobId = defaultRuntimeEngineJobId(input)
        val modelPlugin = input.baseModelRegistryRef.takeIf { it.isNotBlank() }
            ?: return StartRoundExecutionResult.Rejected(
                runtimeEngineJobId = runtimeEngineJobId,
                failureReason = "Execution plan ${input.executionPlanId} does not provide a runtime engine model plugin.",
            )

        val compose = commandRunner.run(properties, listOf("up", "-d", properties.serviceName))
        if (!compose.succeeded) {
            return StartRoundExecutionResult.Unavailable(
                failureReason = if (compose.timedOut) {
                    "Runtime engine docker compose up timed out after ${properties.commandTimeout}."
                } else {
                    "Runtime engine docker compose up failed with exitCode=${compose.exitCode}: ${compose.output}"
                }
            )
        }

        val endpoint = properties.endpoint.trim().removeSuffix("/")
        try {
            waitUntilHealthy(endpoint)
        } catch (ex: Exception) {
            return StartRoundExecutionResult.Unavailable(
                failureReason = "Runtime engine health check failed: ${ex.message ?: ex.javaClass.name}"
            )
        }

        val request = buildJobRequest(input, runtimeEngineJobId, modelPlugin, datasetPath)
        val response = try {
            log.info("Submitting runtime engine job. endpoint={}, request={}", endpoint, request)
            runtimeEngineClient.startJob(endpoint, request)
        } catch (ex: Exception) {
            return StartRoundExecutionResult.Unavailable(
                failureReason = "Runtime engine job submission failed: ${ex.message ?: ex.javaClass.name}"
            )
        }
        if (response.status.equals("failed", ignoreCase = true)) {
            return StartRoundExecutionResult.Rejected(
                runtimeEngineJobId = runtimeEngineJobId,
                failureReason = "Runtime engine rejected job $runtimeEngineJobId: ${response.output}"
            )
        }

        log.info(
            "Started local runtime engine round execution. roundExecutionId={}, executionPlanId={}, runtimeEngineJobId={}, endpoint={}, status={}, output={}",
            input.roundExecutionId,
            input.executionPlanId,
            runtimeEngineJobId,
            endpoint,
            response.status,
            response.output
        )
        return StartRoundExecutionResult.Succeeded(runtimeEngineJobId = runtimeEngineJobId)
    }

    private fun findBinding(input: StartRoundExecutionInput): RuntimeDatasetBindingCatalogReadModel? =
        bindingRepository.findAllByCriteria(
            RuntimeDatasetBindingCatalogReadModelCriteria().apply {
                runtimeId = StringFilter().apply { equals = input.runtimeId.toString() }
                organizationId = StringFilter().apply { equals = input.organizationId.toString() }
            },
            PageRequest.of(0, 20)
        ).content
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
            "Runtime engine at $endpoint did not become healthy within ${properties.healthTimeout}." +
                (lastFailure?.message?.let { " Last error: $it" } ?: "")
        )
    }

    private fun buildJobRequest(
        input: StartRoundExecutionInput,
        runtimeEngineJobId: String,
        modelPlugin: String,
        datasetPath: String
    ): RuntimeEngineJobRequest {
        val localUpdatePath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/local_update.json"
        val metricsPath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/metrics.json"
        val weightArtifactPath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/model_state_dict.pt"
        val modelArtifactPath = "${properties.runtimeRoot}/$runtimeEngineJobId/${properties.nodeName}/model.pt"
        val globalModelPath = input.baseModelArtifactUri
            .takeIf { it.startsWith("/") || it.startsWith("file://") }
            ?.removePrefix("file://")

        val runtimeInput = mutableMapOf<String, Any?>(
            "dataset" to mapOf(
                "path" to toContainerDatasetPath(datasetPath),
                "labelColumn" to properties.labelColumn,
                "id" to properties.idColumn
            )
        )
        if (!globalModelPath.isNullOrBlank()) {
            runtimeInput["globalModel"] = globalModelPath
        }

        return RuntimeEngineJobRequest(
            jobId = runtimeEngineJobId,
            roundId = input.roundNumber,
            nodeName = properties.nodeName,
            role = "trainer",
            operation = "train",
            input = runtimeInput,
            output = mapOf(
                "localUpdate" to localUpdatePath,
                "metrics" to metricsPath,
                "weightArtifact" to weightArtifactPath,
                "modelArtifact" to modelArtifactPath,
            ),
            modelParameter = mapOf(
                "modelPlugin" to modelPlugin,
                "engine" to "python",
                "process" to "train",
                "epochs" to properties.epoch,
                "epoch" to properties.epoch,
                "learningRate" to properties.learningRate,
            ),
            jobParameter = mapOf(
                "encryptMethod" to "plain",
                "loggerLevel" to "INFO"
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

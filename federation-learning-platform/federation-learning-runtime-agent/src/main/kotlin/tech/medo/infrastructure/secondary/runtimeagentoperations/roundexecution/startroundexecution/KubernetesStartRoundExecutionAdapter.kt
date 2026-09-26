package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionService
import java.nio.file.Path
import java.time.Duration
import java.time.Instant

@Component
class KubernetesStartRoundExecutionAdapter(
    private val properties: LocalRuntimeEngineProperties,
    private val manager: RuntimeEngineResourceManager,
    private val runtimeEngineClient: RuntimeEngineClient,
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository
) : StartRoundExecutionService {
    override fun supports(input: StartRoundExecutionInput): Boolean = properties.enabled && properties.usesKubernetes()

    override fun execute(input: StartRoundExecutionInput): StartRoundExecutionResult {
        val binding = bindingRepository.findAllByCriteria(
            RuntimeDatasetBindingCatalogReadModelCriteria().apply {
                runtimeId = StringFilter().apply { equals = input.runtimeId.toString() }
                organizationId = StringFilter().apply { equals = input.organizationId.toString() }
            },
            PageRequest.of(0, 20)
        ).content.filter { it.datasetId != null }.maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }
            ?: return StartRoundExecutionResult.Rejected(null, "No runtime dataset binding is available for runtime ${input.runtimeId}.")
        val datasetPath = binding.filePath?.takeIf(String::isNotBlank)
            ?: return StartRoundExecutionResult.Rejected(
                null,
                "Runtime dataset binding ${binding.runtimeDatasetBindingId} does not provide a filePath."
            )
        val modelPlugin = input.baseModelPlugin.takeIf(String::isNotBlank)
            ?: return StartRoundExecutionResult.Rejected(
                null,
                "Execution plan ${input.executionPlanId} does not provide a runtime engine model plugin."
            )
        val jobId = "round-${input.roundNumber}-${input.executionPlanId}"
        return try {
            manager.ensureStarted(jobId)
            val endpoint = properties.endpointFor(jobId)
            waitUntilHealthy(endpoint)
            val request = jobRequest(input, jobId, modelPlugin, datasetPath)
            val response = runtimeEngineClient.startJob(endpoint, request)
            if (response.status.equals("failed", ignoreCase = true)) {
                manager.delete(jobId)
                StartRoundExecutionResult.Rejected(jobId, "Runtime engine rejected job $jobId: ${response.output}")
            } else {
                log.info("Started Kubernetes runtime engine job. jobId={}, endpoint={}, status={}", jobId, endpoint, response.status)
                StartRoundExecutionResult.Succeeded(jobId)
            }
        } catch (ex: Exception) {
            runCatching { manager.delete(jobId) }
            StartRoundExecutionResult.Unavailable("Kubernetes runtime engine startup failed: ${ex.message ?: ex.javaClass.name}")
        }
    }

    private fun waitUntilHealthy(endpoint: String) {
        val deadline = Instant.now().plus(properties.healthTimeout.coerceAtLeast(Duration.ofSeconds(1)))
        var lastFailure: Exception? = null
        while (Instant.now().isBefore(deadline)) {
            try {
                if (runtimeEngineClient.health(endpoint).status.equals("ok", ignoreCase = true)) return
            } catch (ex: Exception) {
                lastFailure = ex
            }
            Thread.sleep(properties.healthPollInterval.coerceAtLeast(Duration.ofMillis(200)).toMillis())
        }
        throw IllegalStateException(
            "Runtime engine at $endpoint did not become healthy within ${properties.healthTimeout}." +
                (lastFailure?.message?.let { " Last error: $it" } ?: "")
        )
    }

    private fun jobRequest(
        input: StartRoundExecutionInput,
        jobId: String,
        modelPlugin: String,
        datasetPath: String
    ): RuntimeEngineJobRequest {
        val outputRoot = "${properties.runtimeRoot}/$jobId/${properties.nodeName}"
        return RuntimeEngineJobRequest(
            jobId = jobId,
            roundId = input.roundNumber,
            nodeName = properties.nodeName,
            role = "trainer",
            operation = "train",
            input = buildMap {
                put("dataset", datasetInput(datasetPath))
                input.baseModelArtifactUri
                    .takeIf { it.startsWith("/") || it.startsWith("file://") }
                    ?.let { put("globalModel", it.removePrefix("file://")) }
            },
            output = mapOf(
                "localUpdate" to "$outputRoot/local_update.json",
                "metrics" to "$outputRoot/metrics.json",
                "weightArtifact" to "$outputRoot/model_state_dict.pt",
                "modelArtifact" to "$outputRoot/model.pt"
            ),
            modelParameter = mapOf(
                "modelPlugin" to modelPlugin,
                "engine" to "python",
                "process" to "train",
                "epochs" to properties.epoch,
                "epoch" to properties.epoch,
                "learningRate" to properties.learningRate
            ),
            jobParameter = mapOf("encryptMethod" to "plain", "loggerLevel" to "INFO"),
            runtimeRoot = properties.runtimeRoot
        )
    }

    private fun datasetInput(datasetPath: String): Map<String, Any> {
        val base = Path.of(datasetPath)
        val dataset = mutableMapOf<String, Any>(
            "path" to datasetPath,
            "labelColumn" to properties.labelColumn,
            "id" to properties.idColumn
        )
        if (base.resolve("train").toFile().isDirectory) {
            dataset["train"] = "$datasetPath/train"
        }
        if (base.resolve("val").toFile().isDirectory) {
            dataset["val"] = "$datasetPath/val"
        }
        return dataset
    }

    companion object {
        private val log = LoggerFactory.getLogger(KubernetesStartRoundExecutionAdapter::class.java)
    }
}

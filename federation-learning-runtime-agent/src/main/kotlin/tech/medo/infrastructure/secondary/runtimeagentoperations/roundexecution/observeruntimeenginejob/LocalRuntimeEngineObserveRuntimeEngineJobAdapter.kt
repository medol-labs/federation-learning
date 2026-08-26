package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.observeruntimeenginejob

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineClient
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineJobResponse
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobInput
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobResult
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobService
import java.math.BigDecimal
import java.nio.file.Path
import java.time.Duration
import java.time.Instant

@Component
class LocalRuntimeEngineObserveRuntimeEngineJobAdapter(
    private val properties: LocalRuntimeEngineProperties,
    private val runtimeEngineClient: RuntimeEngineClient,
    private val objectMapper: ObjectMapper
) : ObserveRuntimeEngineJobService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: ObserveRuntimeEngineJobInput): Boolean = properties.enabled

    override fun execute(input: ObserveRuntimeEngineJobInput): ObserveRuntimeEngineJobResult {
        val endpoint = properties.endpoint.trim().removeSuffix("/")
        val deadline = Instant.now().plus(properties.jobObservationTimeout.coerceAtLeast(Duration.ZERO))
        var response: RuntimeEngineJobResponse
        do {
            response = try {
                runtimeEngineClient.getJob(endpoint, input.runtimeEngineJobId)
            } catch (ex: Exception) {
                val failureReason = "Runtime engine job ${input.runtimeEngineJobId} could not be observed: ${ex.message ?: ex.javaClass.name}"
                log.warn(failureReason, ex)
                return ObserveRuntimeEngineJobResult.Succeeded(
                    observedStatus = STATUS_FAILED,
                    failureReason = failureReason,
                    localUpdateArtifactRef = null,
                    encryptedUpdateArtifactRef = null,
                    encryptedUpdateDigest = null,
                    metricsArtifactRef = null,
                    trainingLoss = null
                )
            }
            val normalizedStatus = normalizeStatus(response.status)
            log.info(
                "Observed runtime engine job. endpoint={}, runtimeEngineJobId={}, status={}, exitCode={}, output={}",
                endpoint,
                input.runtimeEngineJobId,
                normalizedStatus,
                response.exitCode,
                response.output
            )

            if (normalizedStatus != STATUS_RUNNING) {
                return toResult(input, response, normalizedStatus)
            }

            if (!Instant.now().isBefore(deadline)) {
                return toRunningResult(input, response)
            }
            Thread.sleep(properties.jobObservationPollInterval.coerceAtLeast(Duration.ofMillis(100)).toMillis())
        } while (true)
    }

    private fun toResult(
        input: ObserveRuntimeEngineJobInput,
        response: RuntimeEngineJobResponse,
        normalizedStatus: String
    ): ObserveRuntimeEngineJobResult.Succeeded =
        when (normalizedStatus) {
            STATUS_COMPLETED -> {
                val localUpdateArtifactRef = stringOutput(response, "localUpdate")
                val encryptedUpdateArtifactRef = encryptedUpdateArtifactRef(input, response, localUpdateArtifactRef)
                val metricsArtifactRef = stringOutput(response, "metrics")
                ObserveRuntimeEngineJobResult.Succeeded(
                    observedStatus = STATUS_COMPLETED,
                    failureReason = null,
                    localUpdateArtifactRef = localUpdateArtifactRef,
                    encryptedUpdateArtifactRef = encryptedUpdateArtifactRef,
                    encryptedUpdateDigest = encryptedUpdateDigest(input, response, encryptedUpdateArtifactRef),
                    metricsArtifactRef = metricsArtifactRef,
                    trainingLoss = readTrainingLoss(metricsArtifactRef)
                )
            }
            STATUS_FAILED -> {
                val localUpdateArtifactRef = stringOutput(response, "localUpdate")
                val encryptedUpdateArtifactRef = encryptedUpdateArtifactRef(input, response, localUpdateArtifactRef)
                ObserveRuntimeEngineJobResult.Succeeded(
                    observedStatus = STATUS_FAILED,
                    failureReason = buildFailureReason(input, response),
                    localUpdateArtifactRef = localUpdateArtifactRef,
                    encryptedUpdateArtifactRef = encryptedUpdateArtifactRef,
                    encryptedUpdateDigest = encryptedUpdateDigest(input, response, encryptedUpdateArtifactRef),
                    metricsArtifactRef = stringOutput(response, "metrics"),
                    trainingLoss = readTrainingLoss(stringOutput(response, "metrics"))
                )
            }
            else -> toRunningResult(input, response)
        }

    private fun toRunningResult(input: ObserveRuntimeEngineJobInput, response: RuntimeEngineJobResponse): ObserveRuntimeEngineJobResult.Succeeded {
        val localUpdateArtifactRef = stringOutput(response, "localUpdate")
        val encryptedUpdateArtifactRef = encryptedUpdateArtifactRef(input, response, localUpdateArtifactRef)
        return ObserveRuntimeEngineJobResult.Succeeded(
            observedStatus = STATUS_RUNNING,
            failureReason = null,
            localUpdateArtifactRef = localUpdateArtifactRef,
            encryptedUpdateArtifactRef = encryptedUpdateArtifactRef,
            encryptedUpdateDigest = encryptedUpdateDigest(input, response, encryptedUpdateArtifactRef),
            metricsArtifactRef = stringOutput(response, "metrics"),
            trainingLoss = null
        )
    }

    private fun normalizeStatus(status: String?): String =
        when (status?.trim()?.uppercase()) {
            "COMPLETED", "COMPLETE", "SUCCEEDED", "SUCCESS" -> STATUS_COMPLETED
            "FAILED", "FAILURE", "ERROR", "CANCELLED", "CANCELED" -> STATUS_FAILED
            else -> STATUS_RUNNING
        }

    private fun buildFailureReason(input: ObserveRuntimeEngineJobInput, response: RuntimeEngineJobResponse): String =
        "Runtime engine job ${input.runtimeEngineJobId} finished with status ${response.status ?: "unknown"}" +
            (response.exitCode?.let { " and exitCode=$it" } ?: "") +
            "."

    private fun stringOutput(response: RuntimeEngineJobResponse, key: String): String? =
        response.output[key]?.toString()?.takeIf { it.isNotBlank() }

    private fun encryptedUpdateArtifactRef(
        input: ObserveRuntimeEngineJobInput,
        response: RuntimeEngineJobResponse,
        localUpdateArtifactRef: String?
    ): String? =
        stringOutput(response, "encryptedUpdate")
            ?: stringOutput(response, "encryptedUpdateArtifactRef")
            ?: localUpdateArtifactRef.takeIf { input.secureAggregationRequired }

    private fun encryptedUpdateDigest(
        input: ObserveRuntimeEngineJobInput,
        response: RuntimeEngineJobResponse,
        encryptedUpdateArtifactRef: String?
    ): String? =
        stringOutput(response, "encryptedUpdateDigest")
            ?: stringOutput(response, "localUpdateDigest").takeIf { input.secureAggregationRequired }
            ?: encryptedUpdateArtifactRef?.let { "sha256:local-dev-${"$input.runtimeEngineJobId:$it".sha256Like()}" }

    private fun String.sha256Like(): String =
        hashCode().toUInt().toString(16).padStart(8, '0')

    private fun readTrainingLoss(metricsArtifactRef: String?): BigDecimal? {
        val metricsPath = metricsArtifactRef?.let(::toReadablePath) ?: return null
        if (!metricsPath.toFile().isFile) {
            log.info("Runtime engine metrics file is not readable yet. metricsArtifactRef={}, resolvedPath={}", metricsArtifactRef, metricsPath)
            return null
        }
        return runCatching {
            val node = objectMapper.readTree(metricsPath.toFile())
            node.get("loss")?.decimalValue()
        }.onFailure { ex ->
            log.warn("Failed to read runtime engine metrics. metricsArtifactRef={}, resolvedPath={}", metricsArtifactRef, metricsPath, ex)
        }.getOrNull()
    }

    private fun toReadablePath(artifactRef: String): Path {
        val normalizedRef = artifactRef.removePrefix("file://")
        val runtimeRoot = properties.runtimeRoot.trimEnd('/')
        val runtimeRootHostRoot = properties.runtimeRootHostRoot.trimEnd('/')
        return if (runtimeRoot.isNotBlank() && runtimeRootHostRoot.isNotBlank() && normalizedRef.startsWith("$runtimeRoot/")) {
            Path.of(runtimeRootHostRoot + normalizedRef.removePrefix(runtimeRoot))
        } else {
            Path.of(normalizedRef)
        }
    }

    private companion object {
        private const val STATUS_RUNNING = "RUNNING"
        private const val STATUS_COMPLETED = "COMPLETED"
        private const val STATUS_FAILED = "FAILED"
    }
}

package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.releaseruntimeenginejobafterfailure

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineClient
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineResourceManager
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionResult
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureResult
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureResult
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureResult
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureService

@Component
class LocalRuntimeEngineReleaseRuntimeEngineJobAdapter(
    private val properties: LocalRuntimeEngineProperties,
    private val runtimeEngineClient: RuntimeEngineClient,
    private val runtimeEngineResourceManager: RuntimeEngineResourceManager
) : ReleaseRuntimeEngineJobAfterCompletionService,
    ReleaseRuntimeEngineJobAfterFailureService,
    ReleaseRuntimeEngineJobAfterStartFailureService,
    ReleaseRuntimeEngineJobAfterRetryFailureService,
    ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: ReleaseRuntimeEngineJobAfterCompletionInput): Boolean = properties.enabled

    override fun supports(input: ReleaseRuntimeEngineJobAfterFailureInput): Boolean = properties.enabled

    override fun supports(input: ReleaseRuntimeEngineJobAfterStartFailureInput): Boolean = properties.enabled

    override fun supports(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): Boolean = properties.enabled

    override fun supports(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): Boolean = properties.enabled

    override fun execute(input: ReleaseRuntimeEngineJobAfterCompletionInput): ReleaseRuntimeEngineJobAfterCompletionResult {
        release(input.roundExecutionId.toString(), input.runtimeEngineJobId)
        return ReleaseRuntimeEngineJobAfterCompletionResult.Succeeded()
    }

    override fun execute(input: ReleaseRuntimeEngineJobAfterFailureInput): ReleaseRuntimeEngineJobAfterFailureResult {
        val failureReason = release(input.roundExecutionId.toString(), input.runtimeEngineJobId)
        return ReleaseRuntimeEngineJobAfterFailureResult.Succeeded(
            runtimeEngineReleaseFailureReason = failureReason
        )
    }

    override fun execute(input: ReleaseRuntimeEngineJobAfterStartFailureInput): ReleaseRuntimeEngineJobAfterStartFailureResult {
        val failureReason = release(input.roundExecutionId.toString(), input.runtimeEngineJobId)
        return ReleaseRuntimeEngineJobAfterStartFailureResult.Succeeded(
            runtimeEngineReleaseFailureReason = failureReason
        )
    }

    override fun execute(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): ReleaseRuntimeEngineJobAfterRetryFailureResult {
        val failureReason = release(input.roundExecutionId.toString(), input.runtimeEngineJobId)
        return ReleaseRuntimeEngineJobAfterRetryFailureResult.Succeeded(
            runtimeEngineReleaseFailureReason = failureReason
        )
    }

    override fun execute(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult {
        val failureReason = release(input.roundExecutionId.toString(), input.runtimeEngineJobId)
        return ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult.Succeeded(
            runtimeEngineReleaseFailureReason = failureReason
        )
    }

    private fun release(roundExecutionId: String, runtimeEngineJobId: String?): String? {
        val jobId = runtimeEngineJobId?.takeIf { it.isNotBlank() }
            ?: return "Runtime engine job release skipped because runtimeEngineJobId is empty."
        val endpoint = properties.endpointFor(jobId)
        return try {
            val response = runtimeEngineClient.cancelJob(endpoint, jobId)
            log.info(
                "Released runtime engine job. roundExecutionId={}, runtimeEngineJobId={}, endpoint={}, status={}, exitCode={}",
                roundExecutionId,
                jobId,
                endpoint,
                response.status,
                response.exitCode
            )
            if (properties.usesKubernetes()) {
                runtimeEngineResourceManager.delete(jobId)
            }
            null
        } catch (ex: Exception) {
            val reason = "Runtime engine job $jobId release failed: ${ex.message ?: ex.javaClass.name}"
            log.warn(
                "Runtime engine job release failed. roundExecutionId={}, runtimeEngineJobId={}, endpoint={}, reason={}",
                roundExecutionId,
                jobId,
                endpoint,
                reason
            )
            reason
        }
    }
}

package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import java.util.UUID;

interface ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService {
    fun supports(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): Boolean = true
    fun execute(input: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput): ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult
}

data class ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?
)

sealed interface ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult {
    data class Succeeded(
        val runtimeEngineReleaseFailureReason: String?
    ) : ReleaseRuntimeEngineJobAfterRuntimeRetryFailureResult


}

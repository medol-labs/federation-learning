package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import java.util.UUID;

interface ReleaseRuntimeEngineJobAfterRetryFailureService {
    fun supports(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): Boolean = true
    fun execute(input: ReleaseRuntimeEngineJobAfterRetryFailureInput): ReleaseRuntimeEngineJobAfterRetryFailureResult
}

data class ReleaseRuntimeEngineJobAfterRetryFailureInput(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?
)

sealed interface ReleaseRuntimeEngineJobAfterRetryFailureResult {
    data class Succeeded(
        val runtimeEngineReleaseFailureReason: String?
    ) : ReleaseRuntimeEngineJobAfterRetryFailureResult


}

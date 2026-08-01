package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import java.util.UUID;

interface ReleaseRuntimeEngineJobAfterFailureService {
    fun supports(input: ReleaseRuntimeEngineJobAfterFailureInput): Boolean = true
    fun execute(input: ReleaseRuntimeEngineJobAfterFailureInput): ReleaseRuntimeEngineJobAfterFailureResult
}

data class ReleaseRuntimeEngineJobAfterFailureInput(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?
)

sealed interface ReleaseRuntimeEngineJobAfterFailureResult {
    data class Succeeded(
        val failureReason: String?
    ) : ReleaseRuntimeEngineJobAfterFailureResult


}

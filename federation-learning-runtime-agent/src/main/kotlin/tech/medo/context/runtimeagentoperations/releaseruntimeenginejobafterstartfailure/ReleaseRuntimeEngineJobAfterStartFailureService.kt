package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import java.util.UUID;

interface ReleaseRuntimeEngineJobAfterStartFailureService {
    fun supports(input: ReleaseRuntimeEngineJobAfterStartFailureInput): Boolean = true
    fun execute(input: ReleaseRuntimeEngineJobAfterStartFailureInput): ReleaseRuntimeEngineJobAfterStartFailureResult
}

data class ReleaseRuntimeEngineJobAfterStartFailureInput(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?
)

sealed interface ReleaseRuntimeEngineJobAfterStartFailureResult {
    data class Succeeded(
        val runtimeEngineReleaseFailureReason: String?
    ) : ReleaseRuntimeEngineJobAfterStartFailureResult


}

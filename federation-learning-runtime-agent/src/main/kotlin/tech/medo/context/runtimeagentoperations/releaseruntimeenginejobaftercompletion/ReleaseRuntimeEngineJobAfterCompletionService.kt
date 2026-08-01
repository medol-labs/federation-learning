package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import java.util.UUID;

interface ReleaseRuntimeEngineJobAfterCompletionService {
    fun supports(input: ReleaseRuntimeEngineJobAfterCompletionInput): Boolean = true
    fun execute(input: ReleaseRuntimeEngineJobAfterCompletionInput): ReleaseRuntimeEngineJobAfterCompletionResult
}

data class ReleaseRuntimeEngineJobAfterCompletionInput(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String
)

sealed interface ReleaseRuntimeEngineJobAfterCompletionResult {
    class Succeeded : ReleaseRuntimeEngineJobAfterCompletionResult


}

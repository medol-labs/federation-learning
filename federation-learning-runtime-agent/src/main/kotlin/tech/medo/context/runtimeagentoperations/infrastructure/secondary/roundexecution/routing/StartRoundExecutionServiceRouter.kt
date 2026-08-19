package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionService
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class StartRoundExecutionServiceRouter(private val adapters: ObjectProvider<StartRoundExecutionService>) : StartRoundExecutionService {
    override fun supports(input: StartRoundExecutionInput): Boolean = true

    override fun execute(input: StartRoundExecutionInput): StartRoundExecutionResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                StartRoundExecutionResult.Unavailable(
                    runtimeEngineJobId = null,
                    failureReason = ex.message ?: "StartRoundExecutionService is unavailable."
                )
            }
            0 -> StartRoundExecutionResult.Unavailable(
                runtimeEngineJobId = null,
                failureReason = "No StartRoundExecutionService adapter supports the requested input."
            )
            else -> StartRoundExecutionResult.Unavailable(
                runtimeEngineJobId = null,
                failureReason = "Multiple StartRoundExecutionService adapters support the requested input."
            )
        }
    }
}

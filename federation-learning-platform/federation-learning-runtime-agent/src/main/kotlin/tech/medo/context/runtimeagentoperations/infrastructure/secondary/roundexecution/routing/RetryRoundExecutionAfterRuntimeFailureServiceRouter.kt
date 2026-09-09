package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureInput
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureService
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RetryRoundExecutionAfterRuntimeFailureServiceRouter(private val adapters: ObjectProvider<RetryRoundExecutionAfterRuntimeFailureService>) : RetryRoundExecutionAfterRuntimeFailureService {
    override fun supports(input: RetryRoundExecutionAfterRuntimeFailureInput): Boolean = true

    override fun execute(input: RetryRoundExecutionAfterRuntimeFailureInput): RetryRoundExecutionAfterRuntimeFailureResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No RetryRoundExecutionAfterRuntimeFailureService adapter supports the requested input.")
            else -> error("Multiple RetryRoundExecutionAfterRuntimeFailureService adapters support the requested input.")
        }
    }
}

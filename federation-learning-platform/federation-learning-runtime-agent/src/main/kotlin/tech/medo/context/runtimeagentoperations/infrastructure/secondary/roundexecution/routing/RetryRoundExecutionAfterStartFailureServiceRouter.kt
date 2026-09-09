package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureInput
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureService
import tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure.RetryRoundExecutionAfterStartFailureResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RetryRoundExecutionAfterStartFailureServiceRouter(private val adapters: ObjectProvider<RetryRoundExecutionAfterStartFailureService>) : RetryRoundExecutionAfterStartFailureService {
    override fun supports(input: RetryRoundExecutionAfterStartFailureInput): Boolean = true

    override fun execute(input: RetryRoundExecutionAfterStartFailureInput): RetryRoundExecutionAfterStartFailureResult {
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
            0 -> error("No RetryRoundExecutionAfterStartFailureService adapter supports the requested input.")
            else -> error("Multiple RetryRoundExecutionAfterStartFailureService adapters support the requested input.")
        }
    }
}

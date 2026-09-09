package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateInput
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateService
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SubmitAgentLocalModelUpdateServiceRouter(private val adapters: ObjectProvider<SubmitAgentLocalModelUpdateService>) : SubmitAgentLocalModelUpdateService {
    override fun supports(input: SubmitAgentLocalModelUpdateInput): Boolean = true

    override fun execute(input: SubmitAgentLocalModelUpdateInput): SubmitAgentLocalModelUpdateResult {
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
            0 -> error("No SubmitAgentLocalModelUpdateService adapter supports the requested input.")
            else -> error("Multiple SubmitAgentLocalModelUpdateService adapters support the requested input.")
        }
    }
}

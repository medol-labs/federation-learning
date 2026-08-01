package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanInput
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanService
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class AcceptExecutionPlanServiceRouter(private val adapters: ObjectProvider<AcceptExecutionPlanService>) : AcceptExecutionPlanService {
    override fun supports(input: AcceptExecutionPlanInput): Boolean = true

    override fun execute(input: AcceptExecutionPlanInput): AcceptExecutionPlanResult {
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
            0 -> error("No AcceptExecutionPlanService adapter supports the requested input.")
            else -> error("Multiple AcceptExecutionPlanService adapters support the requested input.")
        }
    }
}

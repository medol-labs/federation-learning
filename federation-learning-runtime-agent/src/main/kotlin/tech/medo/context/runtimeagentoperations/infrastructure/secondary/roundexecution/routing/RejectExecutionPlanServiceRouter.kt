package tech.medo.runtimeagentoperations.infrastructure.secondary.roundexecution.routing

import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanInput
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanService
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RejectExecutionPlanServiceRouter(private val adapters: ObjectProvider<RejectExecutionPlanService>) : RejectExecutionPlanService {
    override fun supports(input: RejectExecutionPlanInput): Boolean = true

    override fun execute(input: RejectExecutionPlanInput): RejectExecutionPlanResult {
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
            0 -> error("No RejectExecutionPlanService adapter supports the requested input.")
            else -> error("Multiple RejectExecutionPlanService adapters support the requested input.")
        }
    }
}

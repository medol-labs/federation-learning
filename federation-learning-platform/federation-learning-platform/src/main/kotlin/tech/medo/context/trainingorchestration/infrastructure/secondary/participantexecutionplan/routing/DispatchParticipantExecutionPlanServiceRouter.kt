package tech.medo.trainingorchestration.infrastructure.secondary.participantexecutionplan.routing

import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanInput
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanService
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DispatchParticipantExecutionPlanServiceRouter(private val adapters: ObjectProvider<DispatchParticipantExecutionPlanService>) : DispatchParticipantExecutionPlanService {
    override fun supports(input: DispatchParticipantExecutionPlanInput): Boolean = true

    override fun execute(input: DispatchParticipantExecutionPlanInput): DispatchParticipantExecutionPlanResult {
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
            0 -> error("No DispatchParticipantExecutionPlanService adapter supports the requested input.")
            else -> error("Multiple DispatchParticipantExecutionPlanService adapters support the requested input.")
        }
    }
}

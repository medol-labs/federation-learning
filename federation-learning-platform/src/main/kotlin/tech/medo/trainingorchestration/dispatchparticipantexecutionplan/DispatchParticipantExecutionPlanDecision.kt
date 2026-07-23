package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand

import tech.medo.trainingorchestration.events.ParticipantExecutionPlanDispatchedEvent
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState


import tech.medo.trainingorchestration.domain.states.ParticipantExecutionPlanStateEnum


@Component
class DispatchParticipantExecutionPlanDecision {
    fun decide(command: DispatchParticipantExecutionPlanCommand, state: ParticipantExecutionPlanState): List<Any> {
        require(state.currentState == ParticipantExecutionPlanStateEnum.PLAN_GENERATED) {
            "DispatchParticipantExecutionPlan requires ParticipantExecutionPlan to be PlanGenerated."
        }
        return listOf(
            ParticipantExecutionPlanDispatchedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelVersionId = command.baseModelVersionId)
        )
    }
}

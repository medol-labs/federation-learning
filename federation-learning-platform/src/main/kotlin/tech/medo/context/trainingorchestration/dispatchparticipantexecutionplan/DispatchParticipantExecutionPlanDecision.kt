package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanResult
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanDispatchedEvent
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState


import tech.medo.trainingorchestration.domain.states.ParticipantExecutionPlanStateEnum


interface DispatchParticipantExecutionPlanDecision {
    fun decide(command: DispatchParticipantExecutionPlanCommand, state: ParticipantExecutionPlanState, portResult: DispatchParticipantExecutionPlanResult): List<Any> {
        require(state.currentState == ParticipantExecutionPlanStateEnum.PLAN_GENERATED) {
            "DispatchParticipantExecutionPlan requires ParticipantExecutionPlan to be PlanGenerated."
        }
        return when (portResult) {
                    is DispatchParticipantExecutionPlanResult.Succeeded -> listOf(ParticipantExecutionPlanDispatchedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelVersionId = command.baseModelVersionId))
                }
    }
}

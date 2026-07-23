package tech.medo.runtimeagentoperations.receiveparticipantexecutionplan

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.receiveparticipantexecutionplan.ReceiveParticipantExecutionPlanCommand

import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





@Component
class ReceiveParticipantExecutionPlanDecision {
    fun decide(command: ReceiveParticipantExecutionPlanCommand): List<Any> {
        return listOf(
            ExecutionPlanReceivedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelVersionId = command.baseModelVersionId)
        )
    }
}

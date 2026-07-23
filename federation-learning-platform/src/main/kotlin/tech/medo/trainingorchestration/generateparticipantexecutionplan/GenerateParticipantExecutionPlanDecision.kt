package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand

import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState





@Component
class GenerateParticipantExecutionPlanDecision {
    fun decide(command: GenerateParticipantExecutionPlanCommand): List<Any> {
        return listOf(
            ParticipantExecutionPlanGeneratedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelVersionId = command.baseModelVersionId)
        )
    }
}

package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SelectTrainingRoundParticipantsDecision {
    fun decide(command: SelectTrainingRoundParticipantsCommand): List<Any> {
        return listOf(
            TrainingRoundParticipantsSelectedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: derive value */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: derive value */, roundId = java.util.UUID.randomUUID() /* TODO: derive value */, roundNumber = 0 /* TODO: derive value */, minimumNodesPerRound = 0 /* TODO: derive value */, selectedOrganizationIds = emptyList() /* TODO: derive value */, selectedRuntimeIds = emptyList() /* TODO: derive value */, selectedParticipants = emptyList() /* TODO: derive value */, selectedOrganizationCount = 0 /* TODO: derive value */, selectedRuntimeCount = 0 /* TODO: derive value */)
        )
    }
}

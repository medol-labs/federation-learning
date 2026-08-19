package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SelectTrainingRoundParticipantsDecision {
    fun decide(command: SelectTrainingRoundParticipantsCommand, portResult: SelectTrainingRoundParticipantsResult, now: java.time.LocalDateTime): List<Any> {
        return when (portResult) {
                    is SelectTrainingRoundParticipantsResult.Succeeded -> listOf(TrainingRoundParticipantsSelectedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = portResult.trainingRunConfigurationId, featureSchemaId = portResult.featureSchemaId, roundId = portResult.roundId, roundNumber = portResult.roundNumber, minimumNodesPerRound = portResult.minimumNodesPerRound, selectedOrganizationIds = portResult.selectedOrganizationIds, selectedRuntimeIds = portResult.selectedRuntimeIds, selectedParticipants = portResult.selectedParticipants, selectedOrganizationCount = portResult.selectedOrganizationCount, selectedRuntimeCount = portResult.selectedRuntimeCount))
                    is SelectTrainingRoundParticipantsResult.Rejected -> listOf(TrainingRoundParticipantSelectionFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = portResult.trainingRunConfigurationId, featureSchemaId = portResult.featureSchemaId, roundId = portResult.roundId, roundNumber = portResult.roundNumber, minimumNodesPerRound = portResult.minimumNodesPerRound, selectedOrganizationIds = portResult.selectedOrganizationIds, selectedRuntimeIds = portResult.selectedRuntimeIds, selectedParticipants = portResult.selectedParticipants, selectedOrganizationCount = portResult.selectedOrganizationCount, selectedRuntimeCount = portResult.selectedRuntimeCount, failureReason = portResult.failureReason))
                    is SelectTrainingRoundParticipantsResult.Unavailable -> listOf(TrainingRoundParticipantSelectionFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: provide trainingRunConfigurationId */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, roundId = java.util.UUID.randomUUID() /* TODO: provide roundId */, roundNumber = 0 /* TODO: provide roundNumber */, minimumNodesPerRound = 0 /* TODO: provide minimumNodesPerRound */, selectedOrganizationIds = emptyList() /* TODO: provide selectedOrganizationIds */, selectedRuntimeIds = emptyList() /* TODO: provide selectedRuntimeIds */, selectedParticipants = emptyList() /* TODO: provide selectedParticipants */, selectedOrganizationCount = 0 /* TODO: provide selectedOrganizationCount */, selectedRuntimeCount = 0 /* TODO: provide selectedRuntimeCount */, failureReason = portResult.failureReason))
                }
    }
}

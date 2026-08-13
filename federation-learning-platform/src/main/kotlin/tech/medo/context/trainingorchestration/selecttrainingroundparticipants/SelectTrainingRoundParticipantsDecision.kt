package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SelectTrainingRoundParticipantsDecision {
    fun decide(command: SelectTrainingRoundParticipantsCommand, portResult: SelectTrainingRoundParticipantsResult): List<Any> {
        return when (portResult) {
                    is SelectTrainingRoundParticipantsResult.Succeeded -> listOf(TrainingRoundParticipantsSelectedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = portResult.trainingRunConfigurationId, featureSchemaId = portResult.featureSchemaId, roundId = portResult.roundId, roundNumber = portResult.roundNumber, minimumNodesPerRound = portResult.minimumNodesPerRound, selectedOrganizationIds = portResult.selectedOrganizationIds, selectedRuntimeIds = portResult.selectedRuntimeIds, selectedParticipants = portResult.selectedParticipants, selectedOrganizationCount = portResult.selectedOrganizationCount, selectedRuntimeCount = portResult.selectedRuntimeCount))
                }
    }
}

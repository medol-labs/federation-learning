package tech.medo.trainingorchestration.starttraininground

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState


import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


@Component
class StartTrainingRoundDecision {
    fun decide(command: StartTrainingRoundCommand, state: TrainingRoundState, portResult: StartTrainingRoundResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == TrainingRoundStateEnum.PARTICIPANTS_SELECTED) {
            "StartTrainingRound requires TrainingRound to be ParticipantsSelected."
        }
        return when (portResult) {
                    is StartTrainingRoundResult.Succeeded -> listOf(TrainingRoundStartedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = portResult.trainingRunConfigurationId, featureSchemaId = portResult.featureSchemaId, roundId = portResult.roundId, roundNumber = portResult.roundNumber, selectedOrganizationIds = portResult.selectedOrganizationIds, selectedRuntimeIds = portResult.selectedRuntimeIds, selectedParticipants = portResult.selectedParticipants, selectedOrganizationCount = portResult.selectedOrganizationCount, selectedRuntimeCount = portResult.selectedRuntimeCount, minimumNodesPerRound = portResult.minimumNodesPerRound))
                    is StartTrainingRoundResult.Rejected -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = portResult.trainingRunConfigurationId, featureSchemaId = portResult.featureSchemaId, roundId = portResult.roundId, roundNumber = portResult.roundNumber, selectedOrganizationIds = portResult.selectedOrganizationIds, selectedRuntimeIds = portResult.selectedRuntimeIds, selectedParticipants = portResult.selectedParticipants, selectedOrganizationCount = portResult.selectedOrganizationCount, selectedRuntimeCount = portResult.selectedRuntimeCount, minimumNodesPerRound = portResult.minimumNodesPerRound, failureReason = "Start Training Round rejected."))
                    is StartTrainingRoundResult.Unavailable -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = command.selectedParticipants, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, failureReason = portResult.failureReason))
                }
    }
}

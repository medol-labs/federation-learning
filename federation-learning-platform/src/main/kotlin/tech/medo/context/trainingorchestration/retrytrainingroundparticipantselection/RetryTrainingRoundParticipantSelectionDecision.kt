package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

import tech.medo.trainingorchestration.retrytrainingroundparticipantselection.RetryTrainingRoundParticipantSelectionCommand


import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionRetryRequestedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface RetryTrainingRoundParticipantSelectionDecision {
    fun decide(command: RetryTrainingRoundParticipantSelectionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingRoundParticipantSelectionRetryRequestedEvent(trainingJobId = command.trainingJobId)
        )
    }
}

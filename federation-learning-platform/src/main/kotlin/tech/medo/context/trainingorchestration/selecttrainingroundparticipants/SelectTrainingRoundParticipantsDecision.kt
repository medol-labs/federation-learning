package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand



interface SelectTrainingRoundParticipantsDecision {
    fun decide(
        command: SelectTrainingRoundParticipantsCommand,
        portResult: SelectTrainingRoundParticipantsResult
    ): List<Any> {
        return when (portResult) {
            is SelectTrainingRoundParticipantsResult.Succeeded -> emptyList()
        }
    }
}

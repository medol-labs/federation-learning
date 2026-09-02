package tech.medo.trainingorchestration.locktrainingrunconfiguration

import tech.medo.trainingorchestration.locktrainingrunconfiguration.LockTrainingRunConfigurationCommand


import tech.medo.trainingorchestration.events.TrainingRunConfigurationLockedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState





interface LockTrainingRunConfigurationDecision {
    fun decide(command: LockTrainingRunConfigurationCommand, state: TrainingRunConfigurationState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            TrainingRunConfigurationLockedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobId = command.trainingJobId)
        )
    }
}

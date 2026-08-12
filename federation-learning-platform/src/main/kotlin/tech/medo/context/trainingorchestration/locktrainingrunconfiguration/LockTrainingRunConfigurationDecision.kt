package tech.medo.trainingorchestration.locktrainingrunconfiguration

import tech.medo.trainingorchestration.locktrainingrunconfiguration.LockTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.events.TrainingRunConfigurationLockedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum





interface LockTrainingRunConfigurationDecision {
    fun decide(command: LockTrainingRunConfigurationCommand, state: TrainingRunConfigurationState): List<Any> {
        if (state.currentState == TrainingRunConfigurationStateEnum.LOCKED) {
            return emptyList()
        }

        require(state.currentState == TrainingRunConfigurationStateEnum.DRAFT) {
            "LockTrainingRunConfiguration requires TrainingRunConfiguration to be Draft."
        }
        requireNotNull(state.minimumNodesPerRound) {
            "TrainingRunConfiguration.minimumNodesPerRound is required to lock training run configuration."
        }
        require(state.minimumNodesPerRound!! > 0) {
            "TrainingRunConfiguration.minimumNodesPerRound must be greater than zero."
        }

        return listOf(
            TrainingRunConfigurationLockedEvent(trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobId = command.trainingJobId)
        )
    }
}

package tech.medo.trainingorchestration.submittrainingjob

import tech.medo.trainingorchestration.submittrainingjob.SubmitTrainingJobCommand


import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState


import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum


interface SubmitTrainingJobDecision {
    fun decide(command: SubmitTrainingJobCommand, state: TrainingJobState): List<Any> {
        require(state.currentState == TrainingJobStateEnum.DRAFT) {
            "SubmitTrainingJob requires TrainingJob to be Draft."
        }
        return listOf(
            TrainingJobSubmittedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = requireNotNull(state.trainingRunConfigurationId) { "trainingRunConfigurationId is required from state." }, federationId = requireNotNull(state.federationId) { "federationId is required from state." }, federationName = state.federationName, configurationName = state.configurationName, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, featureDomain = state.featureDomain, featureSchemaVersion = state.featureSchemaVersion, trainingJobObjective = requireNotNull(state.trainingJobObjective) { "trainingJobObjective is required from state." }, objective = requireNotNull(state.objective) { "objective is required from state." })
        )
    }
}

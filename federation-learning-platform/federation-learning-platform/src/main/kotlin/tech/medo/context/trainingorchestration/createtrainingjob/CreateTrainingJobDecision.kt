package tech.medo.trainingorchestration.createtrainingjob

import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState





interface CreateTrainingJobDecision {
    fun decide(command: CreateTrainingJobCommand, state: TrainingRunConfigurationState): List<Any> {
        return listOf(
            TrainingJobCreatedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, initialModelId = requireNotNull(state.initialModelId) { "initialModelId is required from state." }, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, trainingJobObjective = "" /* TODO: derive value */, objective = command.objective)
        )
    }
}

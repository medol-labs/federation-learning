package tech.medo.trainingorchestration.createtrainingjob

import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.trainingjob.TrainingJobState





interface CreateTrainingJobDecision {
    fun decide(command: CreateTrainingJobCommand): List<Any> {
        return listOf(
            TrainingJobCreatedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, featureSchemaId = java.util.UUID.randomUUID() /* TODO: Use the feature schema selected in the runnable training run configuration. */, trainingRunConfigurationId = command.trainingRunConfigurationId, objective = command.objective)
        )
    }
}

package tech.medo.trainingorchestration.createtrainingjob

import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState





interface CreateTrainingJobDecision {
    fun decide(
        command: CreateTrainingJobCommand,
        trainingRunConfiguration: TrainingRunConfigurationState
    ): List<Any> {
        val configurationId = requireNotNull(trainingRunConfiguration.trainingRunConfigurationId) {
            "TrainingRunConfiguration is required to create training job."
        }
        require(configurationId == command.trainingRunConfigurationId) {
            "TrainingRunConfiguration.trainingRunConfigurationId must match command.trainingRunConfigurationId."
        }
        val federationId = requireNotNull(trainingRunConfiguration.federationId) {
            "TrainingRunConfiguration.federationId is required to create training job."
        }
        require(federationId == command.federationId) {
            "TrainingRunConfiguration.federationId must match command.federationId."
        }
        val featureSchemaId = requireNotNull(trainingRunConfiguration.featureSchemaId) {
            "TrainingRunConfiguration.featureSchemaId is required to create training job."
        }
        val minimumNodesPerRound = requireNotNull(trainingRunConfiguration.minimumNodesPerRound) {
            "TrainingRunConfiguration.minimumNodesPerRound is required to create training job."
        }
        require(minimumNodesPerRound > 0) {
            "TrainingRunConfiguration.minimumNodesPerRound must be greater than zero."
        }

        return listOf(
            TrainingJobCreatedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, featureSchemaId = featureSchemaId, trainingRunConfigurationId = command.trainingRunConfigurationId, minimumNodesPerRound = minimumNodesPerRound, objective = command.objective)
        )
    }
}

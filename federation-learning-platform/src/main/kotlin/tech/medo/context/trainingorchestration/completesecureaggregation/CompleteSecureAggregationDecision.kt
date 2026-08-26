package tech.medo.trainingorchestration.completesecureaggregation

import tech.medo.trainingorchestration.completesecureaggregation.CompleteSecureAggregationCommand

import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface CompleteSecureAggregationDecision {
    fun decide(command: CompleteSecureAggregationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            GlobalModelUpdatedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, secureAggregationSessionId = command.secureAggregationSessionId, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri)
        )
    }
}

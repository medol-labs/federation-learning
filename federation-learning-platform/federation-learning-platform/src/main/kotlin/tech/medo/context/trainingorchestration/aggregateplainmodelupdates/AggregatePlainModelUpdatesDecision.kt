package tech.medo.trainingorchestration.aggregateplainmodelupdates

import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand

import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import tech.medo.trainingorchestration.events.PlainModelAggregationCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface AggregatePlainModelUpdatesDecision {
    fun decide(command: AggregatePlainModelUpdatesCommand, state: TrainingRoundState, portResult: AggregatePlainModelUpdatesResult): List<Any> {
        // TODO: validate child/member state before appending events.
        return when (portResult) {
                    is AggregatePlainModelUpdatesResult.Succeeded -> listOf(
            PlainModelAggregationCompletedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, modelPlugin = command.modelPlugin, aggregatedModelId = command.aggregatedModelId, aggregatedModelName = portResult.aggregatedModelName, aggregatedModelVersion = portResult.aggregatedModelVersion, aggregatedModelDescription = portResult.aggregatedModelDescription, modelSourceType = portResult.modelSourceType, aggregatedModelArtifactUri = portResult.aggregatedModelArtifactUri, aggregatedModelRegistryRef = portResult.aggregatedModelRegistryRef, modelFormat = portResult.modelFormat, modelArtifactDigest = portResult.modelArtifactDigest, aggregatedModelSignatureUri = portResult.aggregatedModelSignatureUri, aggregatedModelSizeBytes = portResult.aggregatedModelSizeBytes)
            )
                }
    }
}

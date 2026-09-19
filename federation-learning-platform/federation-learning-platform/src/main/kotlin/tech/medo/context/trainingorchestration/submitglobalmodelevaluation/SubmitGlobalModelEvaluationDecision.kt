package tech.medo.trainingorchestration.submitglobalmodelevaluation

import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand

import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult
import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SubmitGlobalModelEvaluationDecision {
    fun decide(command: SubmitGlobalModelEvaluationCommand, state: TrainingRoundState, portResult: SubmitGlobalModelEvaluationResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is SubmitGlobalModelEvaluationResult.Succeeded -> listOf(
            GlobalModelEvaluationSubmittedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, aggregatedModelId = command.aggregatedModelId, modelPlugin = command.modelPlugin, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri, globalAccuracy = portResult.globalAccuracy, globalFairnessScore = portResult.globalFairnessScore)
            )
                }
    }
}

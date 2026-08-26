package tech.medo.trainingorchestration.submitglobalmodelevaluation

import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand

import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SubmitGlobalModelEvaluationDecision {
    fun decide(command: SubmitGlobalModelEvaluationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            GlobalModelEvaluationSubmittedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri, globalAccuracy = command.globalAccuracy, globalFairnessScore = command.globalFairnessScore)
        )
    }
}

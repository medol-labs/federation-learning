package tech.medo.trainingorchestration.submitglobalmodelevaluation

import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand

import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SubmitGlobalModelEvaluationDecision {
    fun decide(command: SubmitGlobalModelEvaluationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            GlobalModelEvaluationSubmittedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, aggregatedModelVersionId = command.aggregatedModelVersionId, modelFormat = command.modelFormat, modelHash = command.modelHash, globalAccuracy = command.globalAccuracy, globalFairnessScore = command.globalFairnessScore)
        )
    }
}

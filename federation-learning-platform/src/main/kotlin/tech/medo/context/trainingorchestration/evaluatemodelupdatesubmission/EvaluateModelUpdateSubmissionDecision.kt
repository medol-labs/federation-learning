package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface EvaluateModelUpdateSubmissionDecision {
    fun decide(command: EvaluateModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate child/member state before appending events.
        return listOf(
            ModelUpdateSubmissionAcceptedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, anomalyScore = command.anomalyScore, acceptedModelUpdateCount = requireNotNull(state.acceptedModelUpdateCount) { "acceptedModelUpdateCount is required from state." }, acceptedRuntimeIds = requireNotNull(state.acceptedRuntimeIds) { "acceptedRuntimeIds is required from state." }, minimumNodesPerRound = requireNotNull(state.minimumNodesPerRound) { "minimumNodesPerRound is required from state." }),
            ModelUpdateSubmissionRejectedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, updateArtifactId = requireNotNull(state.updateArtifactId) { "updateArtifactId is required from state." }, anomalyScore = command.anomalyScore, rejectionReason = "" /* TODO: derive value */)
        )
    }
}

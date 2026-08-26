package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface EvaluateModelUpdateSubmissionDecision {
    fun decide(command: EvaluateModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate child/member state before appending events.
        return listOf(
            ModelUpdateSubmissionAcceptedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, updateProtectionType = command.updateProtectionType, anomalyScore = command.anomalyScore, acceptedModelUpdateCount = 0 /* TODO: count(appendDistinct(ModelUpdateSubmissionAccepted.acceptedRuntimeIds, EvaluateModelUpdateSubmission.runtimeId)) */, acceptedRuntimeIds = emptyList() /* TODO: appendDistinct(ModelUpdateSubmissionAccepted.acceptedRuntimeIds, EvaluateModelUpdateSubmission.runtimeId) */, minimumNodesPerRound = requireNotNull(state.minimumNodesPerRound) { "minimumNodesPerRound is required from state." })
        )
    }
}

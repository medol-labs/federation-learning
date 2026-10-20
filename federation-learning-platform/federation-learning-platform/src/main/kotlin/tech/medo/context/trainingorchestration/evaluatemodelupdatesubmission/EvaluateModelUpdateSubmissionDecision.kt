package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand


import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface EvaluateModelUpdateSubmissionDecision {
    fun decide(command: EvaluateModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate child/member state before appending events.
        if (!(java.math.BigDecimal(command.anomalyScore.toString()).compareTo(java.math.BigDecimal("0.95")) < 0)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_ASSERTION_FAILED",
                i18nKey = "errors.commandAssertionFailed",
                args = mapOf("command" to "EvaluateModelUpdateSubmission", "expression" to "assert EvaluateModelUpdateSubmission.anomalyScore < 0.95"),
                message = "Command validation failed."
            )
        }
        return listOf(
            ModelUpdateSubmissionAcceptedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = requireNotNull(state.trainingJobObjective) { "trainingJobObjective is required from state." }, roundId = command.roundId, roundNumber = requireNotNull(state.roundNumber) { "roundNumber is required from state." }, maxRounds = requireNotNull(state.maxRounds) { "maxRounds is required from state." }, minimumAccuracy = requireNotNull(state.minimumAccuracy) { "minimumAccuracy is required from state." }, aggregationAlgorithm = state.aggregationAlgorithm, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, personalizationMode = command.personalizationMode, personalizationScope = command.personalizationScope, personalizedArtifactPolicy = command.personalizedArtifactPolicy, personalizationMetric = command.personalizationMetric, minimumPersonalizationLift = command.minimumPersonalizationLift, personalizationTargetType = command.personalizationTargetType, personalizationTargetId = command.personalizationTargetId, personalizationCohortId = command.personalizationCohortId, personalizationProfileId = command.personalizationProfileId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, updateProtectionType = command.updateProtectionType, personalizedArtifactAvailable = command.personalizedArtifactAvailable, personalizedModelId = command.personalizedModelId, personalizedModelArtifactRef = command.personalizedModelArtifactRef, personalizedModelArtifactDigest = command.personalizedModelArtifactDigest, personalizedMetricsArtifactRef = command.personalizedMetricsArtifactRef, personalizedAccuracy = command.personalizedAccuracy, personalizationLift = command.personalizationLift, anomalyScore = command.anomalyScore, acceptedModelUpdateCount = 0 /* TODO: count(appendDistinct(ModelUpdateSubmissionAccepted.acceptedRuntimeIds, EvaluateModelUpdateSubmission.runtimeId)) */, acceptedRuntimeIds = emptyList() /* TODO: appendDistinct(ModelUpdateSubmissionAccepted.acceptedRuntimeIds, EvaluateModelUpdateSubmission.runtimeId) */, acceptedModelUpdateArtifactRefs = emptyList() /* TODO: appendDistinct(ModelUpdateSubmissionAccepted.acceptedModelUpdateArtifactRefs, EvaluateModelUpdateSubmission.artifactRef) */, minimumNodesPerRound = requireNotNull(state.minimumNodesPerRound) { "minimumNodesPerRound is required from state." }, requiredModelUpdateCount = requireNotNull(state.selectedRuntimeCount) { "requiredModelUpdateCount is required from state." }, plainAggregationReady = command.secureAggregationRequired)
        )
    }
}

package tech.medo.runtimeagentoperations.completeroundexecution

import tech.medo.runtimeagentoperations.completeroundexecution.CompleteRoundExecutionCommand


import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





interface CompleteRoundExecutionDecision {
    fun decide(command: CompleteRoundExecutionCommand, state: RoundExecutionState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RoundExecutionCompletedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, personalizationMode = command.personalizationMode, personalizationScope = command.personalizationScope, personalizedArtifactPolicy = command.personalizedArtifactPolicy, personalizationMetric = command.personalizationMetric, minimumPersonalizationLift = command.minimumPersonalizationLift, personalizationTargetType = command.personalizationTargetType, personalizationTargetId = command.personalizationTargetId, personalizationCohortId = command.personalizationCohortId, personalizationProfileId = command.personalizationProfileId, runtimeEngineJobId = command.runtimeEngineJobId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, localUpdateArtifactRef = command.localUpdateArtifactRef, encryptedUpdateArtifactRef = command.encryptedUpdateArtifactRef, encryptedUpdateDigest = command.encryptedUpdateDigest, modelUpdateArtifactRef = command.modelUpdateArtifactRef, modelUpdateArtifactDigest = command.modelUpdateArtifactDigest, updateProtectionType = command.updateProtectionType, metricsArtifactRef = command.metricsArtifactRef, trainingLoss = command.trainingLoss, personalizedArtifactAvailable = command.personalizedArtifactAvailable, personalizedModelId = command.personalizedModelId, personalizedModelArtifactRef = command.personalizedModelArtifactRef, personalizedModelArtifactDigest = command.personalizedModelArtifactDigest, personalizedMetricsArtifactRef = command.personalizedMetricsArtifactRef, personalizedAccuracy = command.personalizedAccuracy, personalizationLift = command.personalizationLift)
        )
    }
}

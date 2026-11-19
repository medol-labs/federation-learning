package tech.medo.domain.trainingorchestration.evaluatemodelupdatesubmission

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionDecision
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.HistoricalRoundUpdateDispositionRecordedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState

@Component
class EvaluateModelUpdateSubmissionDecisionComponent : EvaluateModelUpdateSubmissionDecision {
    override fun decide(command: EvaluateModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        state.canonicalRoundId?.let { canonical ->
            require(command.modelUpdateSubmissionId in state.recoverySubmissionIds && command.runtimeId in state.selectedRuntimeIds) {
                "Update is not in the audited physical round."
            }
            if (canonical != command.roundId) {
                if (command.modelUpdateSubmissionId in state.reconciledRejectionIds) return emptyList()
                return listOf(HistoricalRoundUpdateDispositionRecordedEvent(
                    modelUpdateSubmissionId = command.modelUpdateSubmissionId,
                    trainingJobId = command.trainingJobId, roundId = command.roundId, canonicalRoundId = canonical,
                    runtimeId = command.runtimeId, updateArtifactId = command.updateArtifactId, artifactDigest = command.artifactDigest,
                    recoveryDigest = requireNotNull(state.recoveryDigest),
                    reconciledRejectionIds = state.reconciledRejectionIds + command.modelUpdateSubmissionId
                ))
            }
        }
        if (command.runtimeId in state.acceptedRuntimeIds) return emptyList()
        val acceptedRuntimeIds = (state.acceptedRuntimeIds + command.runtimeId).distinct()
        val acceptedModelUpdateArtifactRefs =
            (state.acceptedModelUpdateArtifactRefs + command.artifactRef).distinct()
        val minimumNodesPerRound = requireNotNull(state.minimumNodesPerRound) {
            "minimumNodesPerRound is required from state."
        }
        val requiredModelUpdateCount = requireNotNull(state.selectedRuntimeCount) {
            "selectedRuntimeCount is required from state."
        }

        return listOf(
            ModelUpdateSubmissionAcceptedEvent(
                modelUpdateSubmissionId = command.modelUpdateSubmissionId,
                executionSessionId = command.executionSessionId,
                executionPlanId = command.executionPlanId,
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                trainingJobObjective = requireNotNull(state.trainingJobObjective) {
                    "trainingJobObjective is required from state."
                },
                roundId = command.roundId,
                roundNumber = requireNotNull(state.roundNumber) {
                    "roundNumber is required from state."
                },
                maxRounds = requireNotNull(state.maxRounds) {
                    "maxRounds is required from state."
                },
                minimumAccuracy = requireNotNull(state.minimumAccuracy) {
                    "minimumAccuracy is required from state."
                },
                aggregationAlgorithm = state.aggregationAlgorithm,
                runtimeId = command.runtimeId,
                featureSchemaId = command.featureSchemaId,
                personalizationMode = command.personalizationMode,
                personalizationScope = command.personalizationScope,
                personalizedArtifactPolicy = command.personalizedArtifactPolicy,
                personalizationMetric = command.personalizationMetric,
                minimumPersonalizationLift = command.minimumPersonalizationLift,
                personalizationTargetType = command.personalizationTargetType,
                personalizationTargetId = command.personalizationTargetId,
                personalizationCohortId = command.personalizationCohortId,
                personalizationProfileId = command.personalizationProfileId,
                secureAggregationRequired = command.secureAggregationRequired,
                secureAggregationSessionId = command.secureAggregationSessionId,
                encryptionScheme = command.encryptionScheme,
                publicKeyVersion = command.publicKeyVersion,
                updateArtifactId = command.updateArtifactId,
                artifactRef = command.artifactRef,
                artifactDigest = command.artifactDigest,
                updateProtectionType = command.updateProtectionType,
                personalizedArtifactAvailable = command.personalizedArtifactAvailable,
                personalizedModelId = command.personalizedModelId,
                personalizedModelArtifactRef = command.personalizedModelArtifactRef,
                personalizedModelArtifactDigest = command.personalizedModelArtifactDigest,
                personalizedMetricsArtifactRef = command.personalizedMetricsArtifactRef,
                personalizedAccuracy = command.personalizedAccuracy,
                personalizationLift = command.personalizationLift,
                anomalyScore = command.anomalyScore,
                acceptedModelUpdateCount = acceptedRuntimeIds.size,
                acceptedRuntimeIds = acceptedRuntimeIds,
                acceptedModelUpdateArtifactRefs = acceptedModelUpdateArtifactRefs,
                minimumNodesPerRound = minimumNodesPerRound,
                requiredModelUpdateCount = requiredModelUpdateCount,
                plainAggregationReady =
                    !command.secureAggregationRequired &&
                        acceptedRuntimeIds.size >= requiredModelUpdateCount
            )
        )
    }
}

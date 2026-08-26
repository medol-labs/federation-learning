package tech.medo.trainingorchestration.submitmodelupdatesubmission

import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SubmitModelUpdateSubmissionDecision {
    fun decide(command: SubmitModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            ModelUpdateSubmissionReceivedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundExecutionId = command.roundExecutionId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, localModelId = command.localModelId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, updateProtectionType = command.updateProtectionType, trainingLoss = command.trainingLoss)
        )
    }
}

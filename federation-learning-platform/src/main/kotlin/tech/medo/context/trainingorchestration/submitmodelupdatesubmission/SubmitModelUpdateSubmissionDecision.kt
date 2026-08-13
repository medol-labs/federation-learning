package tech.medo.trainingorchestration.submitmodelupdatesubmission

import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface SubmitModelUpdateSubmissionDecision {
    fun decide(command: SubmitModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            ModelUpdateSubmissionReceivedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundExecutionId = command.roundExecutionId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, localModelId = command.localModelId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, trainingLoss = command.trainingLoss)
        )
    }
}

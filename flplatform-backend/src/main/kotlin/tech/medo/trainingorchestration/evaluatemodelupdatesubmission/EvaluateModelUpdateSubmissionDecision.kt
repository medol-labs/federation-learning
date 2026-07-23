package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionRejectedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





@Component
class EvaluateModelUpdateSubmissionDecision {
    fun decide(command: EvaluateModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate child/member state before appending events.
        return listOf(
            ModelUpdateSubmissionAcceptedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, anomalyScore = command.anomalyScore),
            ModelUpdateSubmissionRejectedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, updateArtifactId = java.util.UUID.randomUUID() /* TODO: derive value */, anomalyScore = command.anomalyScore, rejectionReason = "" /* TODO: derive value */)
        )
    }
}

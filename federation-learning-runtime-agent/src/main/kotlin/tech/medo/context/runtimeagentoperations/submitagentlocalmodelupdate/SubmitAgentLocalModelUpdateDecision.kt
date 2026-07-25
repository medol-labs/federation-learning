package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand

import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class SubmitAgentLocalModelUpdateDecision {
    fun decide(command: SubmitAgentLocalModelUpdateCommand, state: RoundExecutionState): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.COMPLETED) {
            "SubmitAgentLocalModelUpdate requires RoundExecution to be Completed."
        }
        return listOf(
            AgentLocalModelUpdateSubmittedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, roundExecutionId = command.roundExecutionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, localModelVersionId = command.localModelVersionId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, trainingLoss = command.trainingLoss)
        )
    }
}

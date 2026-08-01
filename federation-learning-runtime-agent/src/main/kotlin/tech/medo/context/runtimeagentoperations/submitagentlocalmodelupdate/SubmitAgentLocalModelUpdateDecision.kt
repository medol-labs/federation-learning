package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateResult
import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface SubmitAgentLocalModelUpdateDecision {
    fun decide(command: SubmitAgentLocalModelUpdateCommand, state: RoundExecutionState, portResult: SubmitAgentLocalModelUpdateResult): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.COMPLETED) {
            "SubmitAgentLocalModelUpdate requires RoundExecution to be Completed."
        }
        return when (portResult) {
                    is SubmitAgentLocalModelUpdateResult.Succeeded -> listOf(AgentLocalModelUpdateSubmittedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, roundExecutionId = command.roundExecutionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, localModelVersionId = command.localModelVersionId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, trainingLoss = command.trainingLoss))
                }
    }
}

package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateResult
import tech.medo.runtimeagentoperations.events.AgentLocalModelUpdateSubmittedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





interface SubmitAgentLocalModelUpdateDecision {
    fun decide(command: SubmitAgentLocalModelUpdateCommand, state: RoundExecutionState, portResult: SubmitAgentLocalModelUpdateResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is SubmitAgentLocalModelUpdateResult.Succeeded -> listOf(AgentLocalModelUpdateSubmittedEvent(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, roundExecutionId = command.roundExecutionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, runtimeEngineJobId = command.runtimeEngineJobId, localModelId = command.localModelId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, updateProtectionType = command.updateProtectionType, trainingLoss = command.trainingLoss))
                }
    }
}

package tech.medo.runtimeagentoperations.startroundexecution

import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState


import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


interface StartRoundExecutionDecision {
    fun decide(command: StartRoundExecutionCommand, state: RoundExecutionState, portResult: StartRoundExecutionResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == RoundExecutionStateEnum.PLAN_ACCEPTED) {
            "StartRoundExecution requires RoundExecution to be PlanAccepted."
        }
        return when (portResult) {
                    is StartRoundExecutionResult.Succeeded -> listOf(RoundExecutionStartedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, runtimeEngineJobId = portResult.runtimeEngineJobId))
                    is StartRoundExecutionResult.Rejected -> listOf(RoundExecutionStartFailedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, runtimeEngineJobId = portResult.runtimeEngineJobId, failureReason = portResult.failureReason))
                    is StartRoundExecutionResult.Unavailable -> listOf(RoundExecutionStartFailedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, runtimeEngineJobId = null /* TODO: provide runtimeEngineJobId */, failureReason = portResult.failureReason))
                }
    }
}

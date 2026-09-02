package tech.medo.runtimeagentoperations.observeruntimeenginejob

import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobCommand

import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobResult
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





interface ObserveRuntimeEngineJobDecision {
    fun decide(command: ObserveRuntimeEngineJobCommand, state: RoundExecutionState, portResult: ObserveRuntimeEngineJobResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is ObserveRuntimeEngineJobResult.Succeeded -> listOf(RuntimeEngineJobObservedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, runtimeEngineJobId = command.runtimeEngineJobId, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, observedStatus = portResult.observedStatus, failureReason = portResult.failureReason, localUpdateArtifactRef = portResult.localUpdateArtifactRef, encryptedUpdateArtifactRef = portResult.encryptedUpdateArtifactRef, encryptedUpdateDigest = portResult.encryptedUpdateDigest, modelUpdateArtifactRef = portResult.modelUpdateArtifactRef, modelUpdateArtifactDigest = portResult.modelUpdateArtifactDigest, updateProtectionType = portResult.updateProtectionType, metricsArtifactRef = portResult.metricsArtifactRef, trainingLoss = portResult.trainingLoss))
                }
    }
}

package tech.medo.runtimeagentoperations.receiveparticipantexecutionplan

import tech.medo.runtimeagentoperations.receiveparticipantexecutionplan.ReceiveParticipantExecutionPlanCommand

import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





interface ReceiveParticipantExecutionPlanDecision {
    fun decide(command: ReceiveParticipantExecutionPlanCommand): List<Any> {
        return listOf(
            ExecutionPlanReceivedEvent(executionPlanId = command.executionPlanId, roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        )
    }
}

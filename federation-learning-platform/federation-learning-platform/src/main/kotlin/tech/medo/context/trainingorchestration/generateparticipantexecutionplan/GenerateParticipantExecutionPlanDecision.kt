package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand


import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState





interface GenerateParticipantExecutionPlanDecision {
    fun decide(command: GenerateParticipantExecutionPlanCommand): List<Any> {
        return listOf(
            ParticipantExecutionPlanGeneratedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        )
    }
}

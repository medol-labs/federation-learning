package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand


import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState





interface GenerateParticipantExecutionPlanDecision {
    fun decide(command: GenerateParticipantExecutionPlanCommand): List<Any> {
        return listOf(
            ParticipantExecutionPlanGeneratedEvent(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, personalizationMode = command.personalizationMode, personalizationScope = command.personalizationScope, personalizedArtifactPolicy = command.personalizedArtifactPolicy, personalizationMetric = command.personalizationMetric, minimumPersonalizationLift = command.minimumPersonalizationLift, personalizationTargetType = command.personalizationTargetType, personalizationTargetId = command.personalizationTargetId, personalizationCohortId = command.personalizationCohortId, personalizationProfileId = command.personalizationProfileId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelPlugin = command.baseModelPlugin, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, personalizedBaseModelId = command.personalizedBaseModelId, personalizedBaseModelArtifactUri = command.personalizedBaseModelArtifactUri, personalizedBaseModelArtifactDigest = command.personalizedBaseModelArtifactDigest, runtimeEngineProfileId = command.runtimeEngineProfileId, runtimeEngineProfileName = command.runtimeEngineProfileName, runtimeEnginePluginProfile = command.runtimeEnginePluginProfile, runtimeEngineImage = command.runtimeEngineImage, runtimeEngineImageDigest = command.runtimeEngineImageDigest, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        )
    }
}

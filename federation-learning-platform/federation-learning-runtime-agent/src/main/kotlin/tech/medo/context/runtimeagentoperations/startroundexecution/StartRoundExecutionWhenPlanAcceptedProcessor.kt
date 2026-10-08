package tech.medo.runtimeagentoperations.startroundexecution

import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-start-round-execution")
@Component
class StartRoundExecutionWhenPlanAcceptedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(StartRoundExecutionCommand(roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, personalizationMode = event.personalizationMode, personalizationScope = event.personalizationScope, personalizedArtifactPolicy = event.personalizedArtifactPolicy, personalizationMetric = event.personalizationMetric, minimumPersonalizationLift = event.minimumPersonalizationLift, personalizationTargetType = event.personalizationTargetType, personalizationTargetId = event.personalizationTargetId, personalizationCohortId = event.personalizationCohortId, personalizationProfileId = event.personalizationProfileId, baseModelId = event.baseModelId, baseModelArtifactUri = event.baseModelArtifactUri, baseModelRegistryRef = event.baseModelRegistryRef, baseModelPlugin = event.baseModelPlugin, baseModelFormat = event.baseModelFormat, baseModelArtifactDigest = event.baseModelArtifactDigest, baseModelSignatureUri = event.baseModelSignatureUri, personalizedBaseModelId = event.personalizedBaseModelId, personalizedBaseModelArtifactUri = event.personalizedBaseModelArtifactUri, personalizedBaseModelArtifactDigest = event.personalizedBaseModelArtifactDigest, runtimeEngineProfileId = event.runtimeEngineProfileId, runtimeEngineProfileName = event.runtimeEngineProfileName, runtimeEnginePluginProfile = event.runtimeEnginePluginProfile, runtimeEngineImage = event.runtimeEngineImage, runtimeEngineImageDigest = event.runtimeEngineImageDigest, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
}

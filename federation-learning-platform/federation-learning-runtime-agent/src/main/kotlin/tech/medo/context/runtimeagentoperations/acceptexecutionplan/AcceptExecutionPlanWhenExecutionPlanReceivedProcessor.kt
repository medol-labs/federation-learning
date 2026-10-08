package tech.medo.runtimeagentoperations.acceptexecutionplan

import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-accept-execution-plan")
@Component
class AcceptExecutionPlanWhenExecutionPlanReceivedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(AcceptExecutionPlanCommand(executionPlanId = event.executionPlanId, roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, personalizationMode = event.personalizationMode, personalizationScope = event.personalizationScope, personalizedArtifactPolicy = event.personalizedArtifactPolicy, personalizationMetric = event.personalizationMetric, minimumPersonalizationLift = event.minimumPersonalizationLift, personalizationTargetType = event.personalizationTargetType, personalizationTargetId = event.personalizationTargetId, personalizationCohortId = event.personalizationCohortId, personalizationProfileId = event.personalizationProfileId, baseModelId = event.baseModelId, baseModelArtifactUri = event.baseModelArtifactUri, baseModelRegistryRef = event.baseModelRegistryRef, baseModelPlugin = event.baseModelPlugin, baseModelFormat = event.baseModelFormat, baseModelArtifactDigest = event.baseModelArtifactDigest, baseModelSignatureUri = event.baseModelSignatureUri, personalizedBaseModelId = event.personalizedBaseModelId, personalizedBaseModelArtifactUri = event.personalizedBaseModelArtifactUri, personalizedBaseModelArtifactDigest = event.personalizedBaseModelArtifactDigest, runtimeEngineProfileId = event.runtimeEngineProfileId, runtimeEngineProfileName = event.runtimeEngineProfileName, runtimeEnginePluginProfile = event.runtimeEnginePluginProfile, runtimeEngineImage = event.runtimeEngineImage, runtimeEngineImageDigest = event.runtimeEngineImageDigest, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
}

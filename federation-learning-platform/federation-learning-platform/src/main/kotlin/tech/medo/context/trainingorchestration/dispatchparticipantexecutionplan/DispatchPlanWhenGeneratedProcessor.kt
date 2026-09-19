package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-dispatch-participant-execution-plan")
@Component
class DispatchPlanWhenGeneratedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ParticipantExecutionPlanGeneratedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(DispatchParticipantExecutionPlanCommand(executionPlanId = event.executionPlanId, executionSessionId = event.executionSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, baseModelId = event.baseModelId, baseModelArtifactUri = event.baseModelArtifactUri, baseModelRegistryRef = event.baseModelRegistryRef, baseModelPlugin = event.baseModelPlugin, baseModelFormat = event.baseModelFormat, baseModelArtifactDigest = event.baseModelArtifactDigest, baseModelSignatureUri = event.baseModelSignatureUri, runtimeEngineProfileId = event.runtimeEngineProfileId, runtimeEngineProfileName = event.runtimeEngineProfileName, runtimeEnginePluginProfile = event.runtimeEnginePluginProfile, runtimeEngineImage = event.runtimeEngineImage, runtimeEngineImageDigest = event.runtimeEngineImageDigest, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
}

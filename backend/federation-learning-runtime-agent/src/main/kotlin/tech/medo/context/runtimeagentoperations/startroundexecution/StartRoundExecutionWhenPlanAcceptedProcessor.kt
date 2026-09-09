package tech.medo.runtimeagentoperations.startroundexecution

import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class StartRoundExecutionWhenPlanAcceptedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(StartRoundExecutionCommand(roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, baseModelId = event.baseModelId, baseModelArtifactUri = event.baseModelArtifactUri, baseModelRegistryRef = event.baseModelRegistryRef, baseModelFormat = event.baseModelFormat, baseModelArtifactDigest = event.baseModelArtifactDigest, baseModelSignatureUri = event.baseModelSignatureUri, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
}

package tech.medo.runtimeagentoperations.completeroundexecution

import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
import tech.medo.runtimeagentoperations.completeroundexecution.CompleteRoundExecutionCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CompleteRoundExecutionWhenRuntimeEngineJobCompletedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeEngineJobObservedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.observedStatus == "COMPLETED") {
            commandGateway.send(CompleteRoundExecutionCommand(roundExecutionId = event.roundExecutionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, featureSchemaId = event.featureSchemaId, runtimeEngineJobId = event.runtimeEngineJobId, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, localUpdateArtifactRef = event.localUpdateArtifactRef, encryptedUpdateArtifactRef = event.encryptedUpdateArtifactRef, encryptedUpdateDigest = event.encryptedUpdateDigest, modelUpdateArtifactRef = event.modelUpdateArtifactRef, modelUpdateArtifactDigest = event.modelUpdateArtifactDigest, updateProtectionType = event.updateProtectionType, metricsArtifactRef = event.metricsArtifactRef, trainingLoss = event.trainingLoss)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

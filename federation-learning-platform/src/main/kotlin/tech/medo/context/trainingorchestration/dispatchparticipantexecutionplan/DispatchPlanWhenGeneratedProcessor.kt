package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class DispatchPlanWhenGeneratedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ParticipantExecutionPlanGeneratedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(DispatchParticipantExecutionPlanCommand(executionPlanId = event.executionPlanId, executionSessionId = event.executionSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, baseModelId = event.baseModelId, baseModelArtifactUri = event.baseModelArtifactUri, baseModelRegistryRef = event.baseModelRegistryRef, baseModelFormat = event.baseModelFormat, baseModelArtifactDigest = event.baseModelArtifactDigest, baseModelSignatureUri = event.baseModelSignatureUri)).resultMessage
}

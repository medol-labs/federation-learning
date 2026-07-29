package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class GeneratePlanForSelectedRuntimeProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundStartedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.selectedRuntimeCount > 0) {
            commandGateway.send(GenerateParticipantExecutionPlanCommand(executionPlanId = java.util.UUID.randomUUID() /* TODO: provide executionPlanId */, executionSessionId = java.util.UUID.randomUUID() /* TODO: provide executionSessionId */, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = java.util.UUID.randomUUID() /* TODO: provide runtimeId */, organizationId = java.util.UUID.randomUUID() /* TODO: provide organizationId */, baseModelVersionId = java.util.UUID.randomUUID() /* TODO: provide baseModelVersionId */)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

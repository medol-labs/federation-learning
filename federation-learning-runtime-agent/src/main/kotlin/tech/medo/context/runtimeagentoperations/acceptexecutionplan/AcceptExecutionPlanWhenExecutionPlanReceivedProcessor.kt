package tech.medo.runtimeagentoperations.acceptexecutionplan

import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class AcceptExecutionPlanWhenExecutionPlanReceivedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(AcceptExecutionPlanCommand(executionPlanId = event.executionPlanId, executionSessionId = event.executionSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, organizationId = event.organizationId, baseModelVersionId = event.baseModelVersionId)).resultMessage
}

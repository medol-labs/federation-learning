package tech.medo.runtimeagentoperations.acceptexecutionplan

import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class AcceptExecutionPlanWhenLocalRequirementsSatisfiedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ExecutionPlanReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(AcceptExecutionPlanCommand(executionPlanId = event.executionPlanId, executionSessionId = event.executionSessionId, trainingJobId = event.trainingJobId, runtimeId = event.runtimeId, localExecutionRequirementsSatisfied = false /* TODO: provide localExecutionRequirementsSatisfied */, runtimeIdentityMatched = false /* TODO: provide runtimeIdentityMatched */, runtimeDatasetBindingAvailable = false /* TODO: provide runtimeDatasetBindingAvailable */, datasetAccessValidated = false /* TODO: provide datasetAccessValidated */, baseModelAvailable = false /* TODO: provide baseModelAvailable */, trainingConfigurationSupported = false /* TODO: provide trainingConfigurationSupported */, runtimeResourceAvailable = false /* TODO: provide runtimeResourceAvailable */, runtimeAgentIdle = false /* TODO: provide runtimeAgentIdle */)).resultMessage
}

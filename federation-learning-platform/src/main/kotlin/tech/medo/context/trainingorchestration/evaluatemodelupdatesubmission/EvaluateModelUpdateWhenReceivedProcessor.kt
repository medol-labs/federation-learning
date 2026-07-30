package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionReceivedEvent
import tech.medo.trainingorchestration.evaluatemodelupdatesubmission.EvaluateModelUpdateSubmissionCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class EvaluateModelUpdateWhenReceivedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(EvaluateModelUpdateSubmissionCommand(modelUpdateSubmissionId = event.modelUpdateSubmissionId, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, runtimeId = event.runtimeId)).resultMessage
}

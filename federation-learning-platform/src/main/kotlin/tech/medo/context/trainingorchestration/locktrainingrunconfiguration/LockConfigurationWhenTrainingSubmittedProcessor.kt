package tech.medo.trainingorchestration.locktrainingrunconfiguration

import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.locktrainingrunconfiguration.LockTrainingRunConfigurationCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class LockConfigurationWhenTrainingSubmittedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingJobSubmittedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(LockTrainingRunConfigurationCommand(trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: provide trainingRunConfigurationId */, trainingJobId = event.trainingJobId)).resultMessage
}

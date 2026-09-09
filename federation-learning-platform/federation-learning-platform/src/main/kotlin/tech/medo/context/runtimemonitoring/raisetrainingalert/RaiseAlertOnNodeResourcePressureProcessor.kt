package tech.medo.runtimemonitoring.raisetrainingalert

import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.raisetrainingalert.RaiseTrainingAlertCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class RaiseAlertOnNodeResourcePressureProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeNodeResourcePressureDetectedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(RaiseTrainingAlertCommand(nodeId = event.nodeId, trainingJobId = event.trainingJobId, severity = "" /* TODO: provide severity */, message = "" /* TODO: provide message */)).resultMessage
}

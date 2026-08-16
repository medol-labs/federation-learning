package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import java.util.concurrent.CompletableFuture
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class GeneratePlanForSelectedRuntimeProcessor(
    private val commandGateway: CommandGateway,
    private val commandFactory: ParticipantExecutionPlanCommandFactory
) {
    @EventHandler
    fun on(event: TrainingRoundStartedEvent): CompletableFuture<*> {
        val futures = commandFactory.buildCommands(event)
            .map { commandGateway.send(it).resultMessage as CompletableFuture<*> }

        return if (futures.isEmpty()) {
            CompletableFuture.completedFuture(null)
        } else {
            CompletableFuture.allOf(*futures.toTypedArray())
        }
    }
}

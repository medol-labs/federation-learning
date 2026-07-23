package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.detectruntimenodecapacitychange.DetectRuntimeNodeCapacityChangeCommand




@Component
class DetectRuntimeNodeCapacityChangeCommandHandler(
    private val decision: DetectRuntimeNodeCapacityChangeDecision
) {
    @CommandHandler
    fun handle(
        command: DetectRuntimeNodeCapacityChangeCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand





@Component
class DetectRuntimeNodeResourcePressureCommandHandler(
    private val decision: DetectRuntimeNodeResourcePressureDecision
) {
    @CommandHandler
    fun handle(
        command: DetectRuntimeNodeResourcePressureCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

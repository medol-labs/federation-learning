package tech.medo.runtimegovernance.detectruntimecapabilities

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand




@Component
class DetectRuntimeCapabilitiesCommandHandler(
    private val decision: DetectRuntimeCapabilitiesDecision
) {
    @CommandHandler
    fun handle(
        command: DetectRuntimeCapabilitiesCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

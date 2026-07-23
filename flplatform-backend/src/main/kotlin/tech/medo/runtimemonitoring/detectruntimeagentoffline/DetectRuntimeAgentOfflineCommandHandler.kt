package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand




@Component
class DetectRuntimeAgentOfflineCommandHandler(
    private val decision: DetectRuntimeAgentOfflineDecision
) {
    @CommandHandler
    fun handle(
        command: DetectRuntimeAgentOfflineCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

package tech.medo.runtimegovernance.detectruntimecapabilities

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesInput
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesService




@Component
class DetectRuntimeCapabilitiesCommandHandler(
    private val decision: DetectRuntimeCapabilitiesDecision,
    private val detectRuntimeCapabilitiesService: DetectRuntimeCapabilitiesService
) {
    @CommandHandler
    fun handle(
        command: DetectRuntimeCapabilitiesCommand,
        eventAppender: EventAppender
    ) {
        val input = DetectRuntimeCapabilitiesInput(runtimeId = command.runtimeId, capabilityTypes = command.capabilityTypes)
        val portResult = detectRuntimeCapabilitiesService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}

package tech.medo.runtimegovernance.detectruntimecapabilities

import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand

import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent
import tech.medo.runtimegovernance.runtimecapability.RuntimeCapabilityState





@Component
class DetectRuntimeCapabilitiesDecision {
    fun decide(command: DetectRuntimeCapabilitiesCommand): List<Any> {
        return listOf(
            RuntimeCapabilitiesDetectedEvent(runtimeId = command.runtimeId, capabilityTypes = command.capabilityTypes)
        )
    }
}

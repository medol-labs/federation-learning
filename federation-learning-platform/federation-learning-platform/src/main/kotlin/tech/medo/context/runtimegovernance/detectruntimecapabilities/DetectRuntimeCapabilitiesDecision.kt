package tech.medo.runtimegovernance.detectruntimecapabilities

import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand

import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesResult
import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent
import tech.medo.runtimegovernance.runtimecapability.RuntimeCapabilityState





interface DetectRuntimeCapabilitiesDecision {
    fun decide(command: DetectRuntimeCapabilitiesCommand, portResult: DetectRuntimeCapabilitiesResult): List<Any> {
        return when (portResult) {
                    is DetectRuntimeCapabilitiesResult.Succeeded -> listOf(
            RuntimeCapabilitiesDetectedEvent(runtimeId = command.runtimeId, capabilityTypes = command.capabilityTypes)
            )
                }
    }
}

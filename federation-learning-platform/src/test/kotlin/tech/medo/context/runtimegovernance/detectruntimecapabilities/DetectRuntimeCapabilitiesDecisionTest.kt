package tech.medo.runtimegovernance.detectruntimecapabilities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand
import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent


import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesResult
import java.util.UUID;


class DetectRuntimeCapabilitiesDecisionTest {
    @Test
    fun DetectRuntimeCapabilitiesEmitsRuntimeCapabilitiesDetectedEvent() {
        val events = (object : DetectRuntimeCapabilitiesDecision {}).decide(
            DetectRuntimeCapabilitiesCommand(
            runtimeId = java.util.UUID.randomUUID(),
            capabilityTypes = emptyList()
            ),
            portResult = DetectRuntimeCapabilitiesResult.Succeeded(

            )
        )

        assertTrue(events.any { it is RuntimeCapabilitiesDetectedEvent })
    }
}

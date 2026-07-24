package tech.medo.runtimegovernance.detectruntimecapabilities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesCommand
import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent



import java.util.UUID;


class DetectRuntimeCapabilitiesDecisionTest {
    @Test
    fun DetectRuntimeCapabilitiesEmitsRuntimeCapabilitiesDetectedEvent() {
        val events = DetectRuntimeCapabilitiesDecision().decide(
            DetectRuntimeCapabilitiesCommand(
            runtimeId = java.util.UUID.randomUUID(),
            capabilityTypes = emptyList()
            )
        )

        assertTrue(events.any { it is RuntimeCapabilitiesDetectedEvent })
    }
}

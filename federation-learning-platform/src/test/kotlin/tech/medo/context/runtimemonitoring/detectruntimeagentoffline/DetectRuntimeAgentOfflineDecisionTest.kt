package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent



import java.util.UUID;


class DetectRuntimeAgentOfflineDecisionTest {
    @Test
    fun DetectRuntimeAgentOfflineEmitsRuntimeAgentOfflineDetectedEvent() {
        val events = (object : DetectRuntimeAgentOfflineDecision {}).decide(
            DetectRuntimeAgentOfflineCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            offlineReason = ""
            )
        )

        assertTrue(events.any { it is RuntimeAgentOfflineDetectedEvent })
    }
}

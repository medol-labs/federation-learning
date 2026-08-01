package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.detectruntimenodecapacitychange.DetectRuntimeNodeCapacityChangeCommand
import tech.medo.runtimemonitoring.events.RuntimeNodeCapacityChangedEvent



import java.util.UUID;


class DetectRuntimeNodeCapacityChangeDecisionTest {
    @Test
    fun DetectRuntimeNodeCapacityChangeEmitsRuntimeNodeCapacityChangedEvent() {
        val events = (object : DetectRuntimeNodeCapacityChangeDecision {}).decide(
            DetectRuntimeNodeCapacityChangeCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            previousCapacityHash = null,
            currentCapacityHash = "",
            allocatableCpuCores = 0,
            allocatableMemoryGb = 0,
            allocatableGpuCount = 0
            )
        )

        assertTrue(events.any { it is RuntimeNodeCapacityChangedEvent })
    }
}

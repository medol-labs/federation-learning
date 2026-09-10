package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand
import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import java.util.UUID
import java.math.BigDecimal

class DetectRuntimeNodeResourcePressureDecisionTest {
    @Test
    fun DetectRuntimeNodeResourcePressureEmitsRuntimeNodeResourcePressureDetectedEvent() {
        val events = (object : DetectRuntimeNodeResourcePressureDecision {}).decide(
            DetectRuntimeNodeResourcePressureCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            federationId = null,
            federationName = null,
            trainingJobId = null,
            trainingJobObjective = null,
            roundExecutionId = null,
            runtimeNodeName = null,
            pressureType = "",
            observedValue = java.math.BigDecimal.ZERO,
            thresholdValue = java.math.BigDecimal.ZERO
            )
        )

        assertTrue(events.any { it is RuntimeNodeResourcePressureDetectedEvent })
    }
}

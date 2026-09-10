package tech.medo.runtimemonitoring.recordruntimenodeinventory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.recordruntimenodeinventory.RecordRuntimeNodeInventoryCommand
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import java.util.UUID

class RecordRuntimeNodeInventoryDecisionTest {
    @Test
    fun RecordRuntimeNodeInventoryEmitsRuntimeNodeInventoryReportedEvent() {
        val events = (object : RecordRuntimeNodeInventoryDecision {}).decide(
            RecordRuntimeNodeInventoryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeNodeInventoryReportId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeName = null,
            runtimeNodeName = "",
            infrastructureNodeId = null,
            runtimeNodeRole = "",
            nodeReady = false,
            runtimeEngineVersion = null,
            containerEngineVersion = null,
            operatingSystem = null,
            architecture = "",
            inventoryHash = ""
            )
        )

        assertTrue(events.any { it is RuntimeNodeInventoryReportedEvent })
    }
}

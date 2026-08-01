package tech.medo.runtimeagentoperations.reportagentruntimenodeinventory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportagentruntimenodeinventory.ReportAgentRuntimeNodeInventoryCommand
import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeInventoryReportedEvent



import java.util.UUID;


class ReportAgentRuntimeNodeInventoryDecisionTest {
    @Test
    fun ReportAgentRuntimeNodeInventoryEmitsAgentRuntimeNodeInventoryReportedEvent() {
        val events = (object : ReportAgentRuntimeNodeInventoryDecision {}).decide(
            ReportAgentRuntimeNodeInventoryCommand(
            runtimeNodeInventoryReportId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
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

        assertTrue(events.any { it is AgentRuntimeNodeInventoryReportedEvent })
    }
}

package tech.medo.runtimeagentoperations.reportagentruntimenodeinventory

import tech.medo.runtimeagentoperations.reportagentruntimenodeinventory.ReportAgentRuntimeNodeInventoryCommand

import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeInventoryReportedEvent
import tech.medo.runtimeagentoperations.agentruntimenodeinventory.AgentRuntimeNodeInventoryState





interface ReportAgentRuntimeNodeInventoryDecision {
    fun decide(command: ReportAgentRuntimeNodeInventoryCommand): List<Any> {
        return listOf(
            AgentRuntimeNodeInventoryReportedEvent(runtimeNodeInventoryReportId = command.runtimeNodeInventoryReportId, organizationId = command.organizationId, runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimeNodeName = command.runtimeNodeName, infrastructureNodeId = command.infrastructureNodeId, runtimeNodeRole = command.runtimeNodeRole, nodeReady = command.nodeReady, runtimeEngineVersion = command.runtimeEngineVersion, containerEngineVersion = command.containerEngineVersion, operatingSystem = command.operatingSystem, architecture = command.architecture, inventoryHash = command.inventoryHash)
        )
    }
}

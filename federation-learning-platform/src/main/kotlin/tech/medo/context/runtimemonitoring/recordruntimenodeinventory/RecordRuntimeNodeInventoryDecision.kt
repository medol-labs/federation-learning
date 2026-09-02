package tech.medo.runtimemonitoring.recordruntimenodeinventory

import tech.medo.runtimemonitoring.recordruntimenodeinventory.RecordRuntimeNodeInventoryCommand


import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.runtimenodeinventory.RuntimeNodeInventoryState





interface RecordRuntimeNodeInventoryDecision {
    fun decide(command: RecordRuntimeNodeInventoryCommand): List<Any> {
        return listOf(
            RuntimeNodeInventoryReportedEvent(nodeId = command.nodeId, runtimeNodeInventoryReportId = command.runtimeNodeInventoryReportId, organizationId = command.organizationId, runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, runtimeNodeName = command.runtimeNodeName, infrastructureNodeId = command.infrastructureNodeId, runtimeNodeRole = command.runtimeNodeRole, nodeReady = command.nodeReady, runtimeEngineVersion = command.runtimeEngineVersion, containerEngineVersion = command.containerEngineVersion, operatingSystem = command.operatingSystem, architecture = command.architecture, inventoryHash = command.inventoryHash)
        )
    }
}

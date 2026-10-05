package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry.ReportAgentRuntimeNodeResourceTelemetryCommand


import tech.medo.runtimeagentoperations.events.AgentRuntimeNodeResourceTelemetryReportedEvent
import tech.medo.runtimeagentoperations.agentruntimenoderesourcetelemetry.AgentRuntimeNodeResourceTelemetryState





interface ReportAgentRuntimeNodeResourceTelemetryDecision {
    fun decide(command: ReportAgentRuntimeNodeResourceTelemetryCommand): List<Any> {
        return listOf(
            AgentRuntimeNodeResourceTelemetryReportedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeNodeName = command.runtimeNodeName, nodeReady = command.nodeReady, allocatableCpuCores = command.allocatableCpuCores, allocatableMemoryGb = command.allocatableMemoryGb, allocatableGpuCount = command.allocatableGpuCount, allocatedCpuCores = command.allocatedCpuCores, allocatedMemoryGb = command.allocatedMemoryGb, allocatedGpuCount = command.allocatedGpuCount, availableCpuCores = command.availableCpuCores, availableMemoryGb = command.availableMemoryGb, availableGpuCount = command.availableGpuCount, runningWorkloadCount = command.runningWorkloadCount, workloadCapacity = command.workloadCapacity, observedAt = command.observedAt, telemetryRetentionPolicy = command.telemetryRetentionPolicy)
        )
    }
}

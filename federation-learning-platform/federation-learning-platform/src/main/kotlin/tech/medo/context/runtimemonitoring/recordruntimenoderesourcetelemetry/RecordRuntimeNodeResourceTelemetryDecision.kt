package tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry

import tech.medo.runtimemonitoring.recordruntimenoderesourcetelemetry.RecordRuntimeNodeResourceTelemetryCommand


import tech.medo.runtimemonitoring.events.RuntimeNodeResourceTelemetryRecordedEvent
import tech.medo.runtimemonitoring.runtimenoderesourcetelemetry.RuntimeNodeResourceTelemetryState





interface RecordRuntimeNodeResourceTelemetryDecision {
    fun decide(command: RecordRuntimeNodeResourceTelemetryCommand): List<Any> {
        return listOf(
            RuntimeNodeResourceTelemetryRecordedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeNodeName = command.runtimeNodeName, nodeReady = command.nodeReady, allocatableCpuCores = command.allocatableCpuCores, allocatableMemoryGb = command.allocatableMemoryGb, allocatableGpuCount = command.allocatableGpuCount, allocatedCpuCores = command.allocatedCpuCores, allocatedMemoryGb = command.allocatedMemoryGb, allocatedGpuCount = command.allocatedGpuCount, availableCpuCores = command.availableCpuCores, availableMemoryGb = command.availableMemoryGb, availableGpuCount = command.availableGpuCount, runningWorkloadCount = command.runningWorkloadCount, workloadCapacity = command.workloadCapacity, observedAt = command.observedAt, lastResourceSnapshotAt = command.lastResourceSnapshotAt, telemetryRetentionPolicy = command.telemetryRetentionPolicy)
        )
    }
}

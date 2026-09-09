package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import tech.medo.runtimemonitoring.detectruntimenodecapacitychange.DetectRuntimeNodeCapacityChangeCommand


import tech.medo.runtimemonitoring.events.RuntimeNodeCapacityChangedEvent
import tech.medo.runtimemonitoring.runtimenodecapacity.RuntimeNodeCapacityState





interface DetectRuntimeNodeCapacityChangeDecision {
    fun decide(command: DetectRuntimeNodeCapacityChangeCommand): List<Any> {
        return listOf(
            RuntimeNodeCapacityChangedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, previousCapacityHash = command.previousCapacityHash, currentCapacityHash = command.currentCapacityHash, allocatableCpuCores = command.allocatableCpuCores, allocatableMemoryGb = command.allocatableMemoryGb, allocatableGpuCount = command.allocatableGpuCount)
        )
    }
}

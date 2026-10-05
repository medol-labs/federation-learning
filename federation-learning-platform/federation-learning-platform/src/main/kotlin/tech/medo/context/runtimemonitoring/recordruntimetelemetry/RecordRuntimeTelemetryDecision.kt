package tech.medo.runtimemonitoring.recordruntimetelemetry

import tech.medo.runtimemonitoring.recordruntimetelemetry.RecordRuntimeTelemetryCommand


import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState





interface RecordRuntimeTelemetryDecision {
    fun decide(command: RecordRuntimeTelemetryCommand, state: NodeRuntimeHealthState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RuntimeTelemetryRecordedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, federationId = command.federationId, federationName = command.federationName, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundExecutionId = command.roundExecutionId, runtimeNodeName = command.runtimeNodeName, cpuLoad = command.cpuLoad, gpuLoad = command.gpuLoad, memoryLoad = command.memoryLoad, lastHeartbeatAt = command.lastHeartbeatAt, lastRecoveredAt = command.lastRecoveredAt, offlineDetectionPending = command.offlineDetectionPending, recoveryDetectionPending = command.recoveryDetectionPending, resourcePressureDetectionPending = command.resourcePressureDetectionPending, offlineReason = command.offlineReason, recoveryReason = command.recoveryReason, pressureType = command.pressureType, observedValue = command.observedValue, thresholdValue = command.thresholdValue, alertSeverity = command.alertSeverity, alertMessage = command.alertMessage, healthStatus = command.healthStatus, telemetryRetentionPolicy = command.telemetryRetentionPolicy)
        )
    }
}

package tech.medo.runtimeagentoperations.reportagentruntimetelemetry

import tech.medo.runtimeagentoperations.reportagentruntimetelemetry.ReportAgentRuntimeTelemetryCommand


import tech.medo.runtimeagentoperations.events.AgentRuntimeTelemetryReportedEvent
import tech.medo.runtimeagentoperations.agentruntimetelemetry.AgentRuntimeTelemetryState





interface ReportAgentRuntimeTelemetryDecision {
    fun decide(command: ReportAgentRuntimeTelemetryCommand): List<Any> {
        return listOf(
            AgentRuntimeTelemetryReportedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, federationId = command.federationId, trainingJobId = command.trainingJobId, roundExecutionId = command.roundExecutionId, cpuLoad = command.cpuLoad, gpuLoad = command.gpuLoad, memoryLoad = command.memoryLoad, lastHeartbeatAt = command.lastHeartbeatAt, telemetryRetentionPolicy = command.telemetryRetentionPolicy)
        )
    }
}

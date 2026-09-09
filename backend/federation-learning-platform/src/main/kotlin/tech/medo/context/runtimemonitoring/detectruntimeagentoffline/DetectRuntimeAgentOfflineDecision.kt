package tech.medo.runtimemonitoring.detectruntimeagentoffline

import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand


import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState





interface DetectRuntimeAgentOfflineDecision {
    fun decide(command: DetectRuntimeAgentOfflineCommand): List<Any> {
        return listOf(
            RuntimeAgentOfflineDetectedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, offlineReason = command.offlineReason)
        )
    }
}

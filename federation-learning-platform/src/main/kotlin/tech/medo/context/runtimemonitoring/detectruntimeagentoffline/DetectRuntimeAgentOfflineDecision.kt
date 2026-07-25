package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand

import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState





@Component
class DetectRuntimeAgentOfflineDecision {
    fun decide(command: DetectRuntimeAgentOfflineCommand): List<Any> {
        return listOf(
            RuntimeAgentOfflineDetectedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, offlineReason = command.offlineReason)
        )
    }
}

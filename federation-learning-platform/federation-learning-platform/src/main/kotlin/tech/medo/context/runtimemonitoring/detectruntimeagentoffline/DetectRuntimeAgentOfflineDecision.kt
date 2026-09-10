package tech.medo.runtimemonitoring.detectruntimeagentoffline

import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand


import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState





interface DetectRuntimeAgentOfflineDecision {
    fun decide(command: DetectRuntimeAgentOfflineCommand): List<Any> {
        return listOf(
            RuntimeAgentOfflineDetectedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, federationId = command.federationId, federationName = command.federationName, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundExecutionId = command.roundExecutionId, runtimeNodeName = command.runtimeNodeName, offlineReason = command.offlineReason)
        )
    }
}

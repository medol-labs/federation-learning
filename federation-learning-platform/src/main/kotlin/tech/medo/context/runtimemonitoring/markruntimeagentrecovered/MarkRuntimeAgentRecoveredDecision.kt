package tech.medo.runtimemonitoring.markruntimeagentrecovered

import tech.medo.runtimemonitoring.markruntimeagentrecovered.MarkRuntimeAgentRecoveredCommand

import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState


import tech.medo.runtimemonitoring.domain.states.NodeRuntimeHealthStateEnum


interface MarkRuntimeAgentRecoveredDecision {
    fun decide(command: MarkRuntimeAgentRecoveredCommand, state: NodeRuntimeHealthState): List<Any> {
        require(state.currentState == NodeRuntimeHealthStateEnum.OFFLINE) {
            "MarkRuntimeAgentRecovered requires NodeRuntimeHealth to be Offline."
        }
        return listOf(
            RuntimeAgentRecoveredEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, recoveryReason = command.recoveryReason)
        )
    }
}

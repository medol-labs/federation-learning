package tech.medo.runtimemonitoring.markruntimeagentrecovered

import tech.medo.runtimemonitoring.markruntimeagentrecovered.MarkRuntimeAgentRecoveredCommand


import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthState


import tech.medo.runtimemonitoring.domain.states.NodeRuntimeHealthStateEnum


interface MarkRuntimeAgentRecoveredDecision {
    fun decide(command: MarkRuntimeAgentRecoveredCommand, state: NodeRuntimeHealthState): List<Any> {
        if (state.currentState != NodeRuntimeHealthStateEnum.Offline) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimemonitoring.markRuntimeAgentRecovered.requiresState",
                args = mapOf(
                    "command" to "MarkRuntimeAgentRecovered",
                    "aggregate" to "NodeRuntimeHealth",
                    "expectedState" to "Offline",
                    "actualState" to state.currentState.toString()
                ),
                message = "MarkRuntimeAgentRecovered requires NodeRuntimeHealth to be Offline."
            )
        }
        return listOf(
            RuntimeAgentRecoveredEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, federationId = command.federationId, federationName = command.federationName, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundExecutionId = command.roundExecutionId, runtimeNodeName = command.runtimeNodeName, recoveryReason = command.recoveryReason)
        )
    }
}

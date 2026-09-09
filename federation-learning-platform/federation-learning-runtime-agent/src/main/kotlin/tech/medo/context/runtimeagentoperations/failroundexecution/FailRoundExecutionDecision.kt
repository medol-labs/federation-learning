package tech.medo.runtimeagentoperations.failroundexecution

import tech.medo.runtimeagentoperations.failroundexecution.FailRoundExecutionCommand


import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState





interface FailRoundExecutionDecision {
    fun decide(command: FailRoundExecutionCommand, state: RoundExecutionState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RoundExecutionFailedEvent(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, runtimeEngineJobId = command.runtimeEngineJobId, failureReason = command.failureReason)
        )
    }
}

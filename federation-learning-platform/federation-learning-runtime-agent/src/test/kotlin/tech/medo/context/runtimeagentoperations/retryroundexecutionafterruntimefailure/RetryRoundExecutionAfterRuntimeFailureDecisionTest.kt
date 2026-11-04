package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureCommand
import tech.medo.runtimeagentoperations.events.RoundExecutionFailedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionRuntimeRetryFailedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure.RetryRoundExecutionAfterRuntimeFailureResult
import java.util.UUID
import java.time.LocalDateTime

class RetryRoundExecutionAfterRuntimeFailureDecisionTest {
    @Test
    fun Retryfails() {
        val state = RoundExecutionState()
        state.evolve(
            RoundExecutionFailedEvent(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = null,
            failureReason = null
            )
        )

        val command = RetryRoundExecutionAfterRuntimeFailureCommand(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = "",
            retryReason = ""
        )

        val events = (object : RetryRoundExecutionAfterRuntimeFailureDecision {}).decide(
            command,
            state = state,
            portResult = RetryRoundExecutionAfterRuntimeFailureResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RoundExecutionRuntimeRetryFailedEvent>().single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.baseModelId, event.baseModelId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
        assertEquals(command.retryReason, event.retryReason)
    }
}

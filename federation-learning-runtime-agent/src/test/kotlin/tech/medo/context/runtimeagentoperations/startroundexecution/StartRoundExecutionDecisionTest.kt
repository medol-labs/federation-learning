package tech.medo.runtimeagentoperations.startroundexecution

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RoundExecutionStartFailedEvent

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionResult
import java.util.UUID;
import java.time.LocalDateTime

class StartRoundExecutionDecisionTest {
    @Test
    fun RoundExecutionStarted() {
        val state = RoundExecutionState()
        state.evolve(
            ExecutionPlanAcceptedEvent(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID(),
            localExecutionRequirementsSatisfied = false,
            runtimeIdentityMatched = false,
            runtimeDatasetBindingAvailable = false,
            datasetAccessValidated = false,
            baseModelAvailable = false,
            trainingConfigurationSupported = false,
            runtimeResourceAvailable = false,
            runtimeAgentIdle = false
            )
        )

        val command = StartRoundExecutionCommand(
            roundExecutionId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = ""
        )

        val events = (object : StartRoundExecutionDecision {}).decide(
            command,
            state = state,
            portResult = StartRoundExecutionResult.Succeeded(

            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RoundExecutionStartedEvent>().single()
        assertEquals(command.roundExecutionId, event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()), event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.baseModelVersionId, event.baseModelVersionId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
    }

    @Test
    fun RoundExecutionStartFailed() {
        val state = RoundExecutionState()
        state.evolve(
            ExecutionPlanAcceptedEvent(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID(),
            localExecutionRequirementsSatisfied = false,
            runtimeIdentityMatched = false,
            runtimeDatasetBindingAvailable = false,
            datasetAccessValidated = false,
            baseModelAvailable = false,
            trainingConfigurationSupported = false,
            runtimeResourceAvailable = false,
            runtimeAgentIdle = false
            )
        )

        val command = StartRoundExecutionCommand(
            roundExecutionId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = ""
        )

        val events = (object : StartRoundExecutionDecision {}).decide(
            command,
            state = state,
            portResult = StartRoundExecutionResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RoundExecutionStartFailedEvent>().single()
        assertEquals(command.roundExecutionId, event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()), event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.baseModelVersionId, event.baseModelVersionId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
    }
}

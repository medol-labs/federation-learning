package tech.medo.runtimeagentoperations.acceptexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import tech.medo.runtimeagentoperations.events.ExecutionPlanReceivedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanAcceptedEvent
import tech.medo.runtimeagentoperations.events.ExecutionPlanRejectedEvent

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanResult
import java.util.UUID;
import java.time.LocalDateTime

class AcceptExecutionPlanDecisionTest {
    @Test
    fun ExecutionPlanAccepted() {
        val state = RoundExecutionState()
        state.evolve(
            ExecutionPlanReceivedEvent(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID()
            )
        )

        val command = AcceptExecutionPlanCommand(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID()
        )

        val events = (object : AcceptExecutionPlanDecision {}).decide(
            command,
            state = state,
            portResult = AcceptExecutionPlanResult.Succeeded(
                localExecutionRequirementsSatisfied = false,
                runtimeIdentityMatched = false,
                runtimeDatasetBindingAvailable = false,
                datasetAccessValidated = false,
                baseModelAvailable = false,
                trainingConfigurationSupported = false,
                runtimeResourceAvailable = false,
                runtimeAgentIdle = false
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<ExecutionPlanAcceptedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("execution-plan-1".toByteArray()), event.executionPlanId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.baseModelVersionId, event.baseModelVersionId)
    }

    @Test
    fun ExecutionPlanRejected() {
        val state = RoundExecutionState()
        state.evolve(
            ExecutionPlanReceivedEvent(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID()
            )
        )

        val command = AcceptExecutionPlanCommand(
            executionPlanId = UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelVersionId = java.util.UUID.randomUUID()
        )

        val events = (object : AcceptExecutionPlanDecision {}).decide(
            command,
            state = state,
            portResult = AcceptExecutionPlanResult.Rejected(
                localExecutionRequirementsSatisfied = false,
                runtimeIdentityMatched = false,
                runtimeDatasetBindingAvailable = false,
                datasetAccessValidated = false,
                baseModelAvailable = false,
                trainingConfigurationSupported = false,
                runtimeResourceAvailable = false,
                runtimeAgentIdle = false,
                rejectionReasons = emptyList()
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<ExecutionPlanRejectedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("execution-plan-2".toByteArray()), event.executionPlanId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.baseModelVersionId, event.baseModelVersionId)
    }
}

package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent



import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import java.math.BigDecimal;


class GenerateParticipantExecutionPlanDecisionTest {
    @Test
    fun GenerateInitialRoundPlanWithInitialModelSnapshot() {


        val command = GenerateParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 1,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null
        )

        val events = (object : GenerateParticipantExecutionPlanDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<ParticipantExecutionPlanGeneratedEvent>().single()
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(1, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.baseModelId, event.baseModelId)
        assertEquals(command.baseModelArtifactUri, event.baseModelArtifactUri)
        assertEquals(command.baseModelRegistryRef, event.baseModelRegistryRef)
        assertEquals(command.baseModelFormat, event.baseModelFormat)
        assertEquals(command.baseModelArtifactDigest, event.baseModelArtifactDigest)
        assertEquals(command.baseModelSignatureUri, event.baseModelSignatureUri)
    }

    @Test
    fun GenerateLaterRoundPlanWithAggregatedModelSnapshot() {


        val command = GenerateParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-2".toByteArray()),
            roundNumber = 2,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null
        )

        val events = (object : GenerateParticipantExecutionPlanDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<ParticipantExecutionPlanGeneratedEvent>().single()
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-2".toByteArray()), event.roundId)
        assertEquals(2, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.baseModelId, event.baseModelId)
        assertEquals(command.baseModelArtifactUri, event.baseModelArtifactUri)
        assertEquals(command.baseModelRegistryRef, event.baseModelRegistryRef)
        assertEquals(command.baseModelFormat, event.baseModelFormat)
        assertEquals(command.baseModelArtifactDigest, event.baseModelArtifactDigest)
        assertEquals(command.baseModelSignatureUri, event.baseModelSignatureUri)
    }
}

package tech.medo.runtimeagentoperations.observeruntimeenginejob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobCommand
import tech.medo.runtimeagentoperations.events.RoundExecutionStartedEvent
import tech.medo.runtimeagentoperations.events.RuntimeEngineJobObservedEvent
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobResult
import java.util.UUID
import java.math.BigDecimal

class ObserveRuntimeEngineJobDecisionTest {
    @Test
    fun RuntimeEngineJobObservedRunning() {
        val state = RoundExecutionState()
        state.evolve(
            RoundExecutionStartedEvent(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-1".toByteArray()),
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
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null,
            runtimeEngineJobId = ""
            )
        )

        val command = ObserveRuntimeEngineJobCommand(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-1".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = "",
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null
        )

        val events = (object : ObserveRuntimeEngineJobDecision {}).decide(
            command,
            state = state,
            portResult = ObserveRuntimeEngineJobResult.Succeeded(
                observedStatus = "",
                failureReason = null,
                localUpdateArtifactRef = null,
                encryptedUpdateArtifactRef = null,
                encryptedUpdateDigest = null,
                metricsArtifactRef = null,
                trainingLoss = null
            )
        )

        val event = events.filterIsInstance<RuntimeEngineJobObservedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("round-execution-1".toByteArray()), event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
    }

    @Test
    fun RuntimeEngineJobObservedCompleted() {
        val state = RoundExecutionState()
        state.evolve(
            RoundExecutionStartedEvent(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-2".toByteArray()),
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
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null,
            runtimeEngineJobId = ""
            )
        )

        val command = ObserveRuntimeEngineJobCommand(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-2".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = "",
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null
        )

        val events = (object : ObserveRuntimeEngineJobDecision {}).decide(
            command,
            state = state,
            portResult = ObserveRuntimeEngineJobResult.Succeeded(
                observedStatus = "",
                failureReason = null,
                localUpdateArtifactRef = null,
                encryptedUpdateArtifactRef = null,
                encryptedUpdateDigest = null,
                metricsArtifactRef = null,
                trainingLoss = null
            )
        )

        val event = events.filterIsInstance<RuntimeEngineJobObservedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("round-execution-2".toByteArray()), event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
    }

    @Test
    fun RuntimeEngineJobObservedFailed() {
        val state = RoundExecutionState()
        state.evolve(
            RoundExecutionStartedEvent(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-3".toByteArray()),
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
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null,
            runtimeEngineJobId = ""
            )
        )

        val command = ObserveRuntimeEngineJobCommand(
            roundExecutionId = UUID.nameUUIDFromBytes("round-execution-3".toByteArray()),
            executionSessionId = java.util.UUID.randomUUID(),
            executionPlanId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            runtimeEngineJobId = "",
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null
        )

        val events = (object : ObserveRuntimeEngineJobDecision {}).decide(
            command,
            state = state,
            portResult = ObserveRuntimeEngineJobResult.Succeeded(
                observedStatus = "",
                failureReason = null,
                localUpdateArtifactRef = null,
                encryptedUpdateArtifactRef = null,
                encryptedUpdateDigest = null,
                metricsArtifactRef = null,
                trainingLoss = null
            )
        )

        val event = events.filterIsInstance<RuntimeEngineJobObservedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("round-execution-3".toByteArray()), event.roundExecutionId)
        assertEquals(command.executionSessionId, event.executionSessionId)
        assertEquals(command.executionPlanId, event.executionPlanId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.runtimeEngineJobId, event.runtimeEngineJobId)
        assertEquals(command.secureAggregationRequired, event.secureAggregationRequired)
        assertEquals(command.secureAggregationSessionId, event.secureAggregationSessionId)
        assertEquals(command.encryptionScheme, event.encryptionScheme)
        assertEquals(command.publicKeyVersion, event.publicKeyVersion)
    }
}

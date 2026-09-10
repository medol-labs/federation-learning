package tech.medo.modelrepository.registerfederatedmodelartifact

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modelrepository.registerfederatedmodelartifact.RegisterFederatedModelArtifactCommand
import tech.medo.modelrepository.events.FederatedModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState
import java.util.UUID

class RegisterFederatedModelArtifactDecisionTest {
    @Test
    fun RegisterAggregatedRoundModel() {
        val state = ModelArtifactState()


        val command = RegisterFederatedModelArtifactCommand(
            modelId = java.util.UUID.randomUUID(),
            modelName = "",
            modelVersion = "",
            modelDescription = null,
            sourceType = "",
            modelArtifactUri = "",
            modelRegistryRef = "",
            modelFormat = "",
            modelArtifactDigest = "",
            modelSignatureUri = null,
            modelSizeBytes = null,
            trainingJobId = java.util.UUID.randomUUID(),
            trainingJobObjective = "",
            roundId = java.util.UUID.randomUUID()
        )

        val events = (object : RegisterFederatedModelArtifactDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FederatedModelArtifactRegisteredEvent>().single()
        assertEquals(command.modelId, event.modelId)
        assertEquals(command.modelName, event.modelName)
        assertEquals(command.modelVersion, event.modelVersion)
        assertEquals(command.modelDescription, event.modelDescription)
        assertEquals(command.sourceType, event.sourceType)
        assertEquals(command.modelArtifactUri, event.modelArtifactUri)
        assertEquals(command.modelRegistryRef, event.modelRegistryRef)
        assertEquals(command.modelFormat, event.modelFormat)
        assertEquals(command.modelArtifactDigest, event.modelArtifactDigest)
        assertEquals(command.modelSignatureUri, event.modelSignatureUri)
        assertEquals(command.modelSizeBytes, event.modelSizeBytes)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
        assertEquals(command.roundId, event.roundId)
    }
}

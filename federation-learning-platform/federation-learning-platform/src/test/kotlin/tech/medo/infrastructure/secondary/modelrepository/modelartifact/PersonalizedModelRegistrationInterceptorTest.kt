package tech.medo.infrastructure.secondary.modelrepository.modelartifact

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import tech.medo.modelrepository.registerpersonalizedmodelartifact.RegisterPersonalizedModelArtifactCommand
import tech.medo.trainingorchestration.trainingjobdashboard.*
import tech.medo.trainingorchestration.trainingroundprogress.*
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.*
import java.util.UUID

class PersonalizedModelRegistrationInterceptorTest {
    @Test
    fun resolvesUniqueAggregateIdentityAndModelMetadataBeforeDispatch() {
        val jobs = mock(TrainingJobDashboardReadModelRepository::class.java)
        val configs = mock(TrainingRunConfigurationCatalogReadModelRepository::class.java)
        val rounds = mock(TrainingRoundProgressReadModelRepository::class.java)
        val jobId = UUID.randomUUID()
        val configId = UUID.randomUUID()
        val roundId = UUID.randomUUID()
        val baseId = UUID.randomUUID()
        `when`(jobs.findProjectionById(jobId)).thenReturn(TrainingJobDashboardReadModelProjection().apply {
            trainingRunConfigurationId = configId
            objective = "personalized training"
        })
        `when`(configs.findProjectionById(configId)).thenReturn(TrainingRunConfigurationCatalogReadModelProjection().apply {
            initialModelPlugin = "PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER"
            initialModelFormat = "PYTORCH_STATE_DICT"
        })
        `when`(rounds.findProjectionById(TrainingRoundProgressReadModelKey(jobId, roundId)))
            .thenReturn(TrainingRoundProgressReadModelProjection().apply { roundNumber = 1; baseModelId = baseId })
        val resolver = PersonalizedModelRegistrationInterceptor(jobs, configs, rounds)
        val command = RegisterPersonalizedModelArtifactCommand(
            modelId = UUID.randomUUID(), modelPlugin = "", modelScope = "PARTICIPANT",
            modelArtifactUri = "file:///runtime/personalized.pt", modelFormat = "",
            modelArtifactDigest = "sha256:" + "a".repeat(64), personalizedModelStorageLocation = "RUNTIME_LOCAL",
            platformManagedArtifactRef = null, trainingJobId = jobId, trainingJobObjective = "", roundId = roundId,
            personalizationMode = "LOCAL_ADAPTATION", personalizationTargetType = "PARTICIPANT",
            personalizationTargetId = UUID.randomUUID(), personalizationCohortId = null, baseGlobalModelId = null
        )
        val first = resolver.resolve(command)
        val second = resolver.resolve(command.copy(modelId = UUID.randomUUID(), personalizationTargetId = UUID.randomUUID()))
        assertNotEquals(first.selection, second.selection)
        assertFalse(first.modelName.isBlank())
        assertFalse(first.modelVersion.isBlank())
        assertEquals("PYTORCH_STATE_DICT", first.modelFormat)
        assertEquals("FEDERATED_TRAINING", first.sourceType)
        assertEquals(baseId, first.baseGlobalModelId)
        assertEquals("personalized training", first.trainingJobObjective)
        assertEquals(command.modelId, first.modelId)
        assertEquals(command.modelArtifactDigest, first.modelArtifactDigest)
    }
}

package tech.medo.infrastructure.secondary.modelrepository.modelartifact.downloadmodelartifact

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactInput
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModel
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelCriteria
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import java.util.UUID

class ReadModelDownloadModelArtifactAdapterTest {
    @Test
    fun resolvesRegisteredModelArtifact() {
        val adapter = ReadModelDownloadModelArtifactAdapter(FakeRepository(artifact()))

        val result = adapter.execute(DownloadModelArtifactInput(MODEL_ID)) as DownloadModelArtifactResult.Succeeded

        assertEquals("credit-risk", result.modelName)
        assertEquals("v1", result.modelVersion)
        assertEquals("JSON", result.modelFormat)
        assertEquals("sha256:model", result.modelArtifactDigest)
        assertEquals(DOWNLOAD_URI, result.downloadUri)
    }

    @Test
    fun rejectsArtifactWithoutDownloadUri() {
        val adapter = ReadModelDownloadModelArtifactAdapter(
            FakeRepository(artifact(modelArtifactUri = null))
        )

        assertThrows(IllegalStateException::class.java) {
            adapter.execute(DownloadModelArtifactInput(MODEL_ID))
        }
    }

    private fun artifact(modelArtifactUri: String? = DOWNLOAD_URI) = ModelArtifactCatalogReadModel(
        modelId = MODEL_ID,
        modelName = "credit-risk",
        modelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
        modelVersion = "v1",
        modelDescription = null,
        sourceType = "FEDERATED",
        modelArtifactUri = modelArtifactUri,
        modelRegistryRef = "http://support:8080/api/files/$MODEL_ID",
        modelFormat = "JSON",
        modelArtifactDigest = "sha256:model",
        modelSignatureUri = null,
        modelSizeBytes = 1024,
        trainingJobId = null,
        roundId = null,
        trainingJobObjective = null,
        state = ModelArtifactStateEnum.REGISTERED,
        registeredAt = null,
        userId = null,
        sessionId = null,
        correlationId = null,
        causationId = null,
        traceId = null,
        tenantId = null
    )

    private class FakeRepository(
        private val artifact: ModelArtifactCatalogReadModel?
    ) : ModelArtifactCatalogReadModelRepository {
        override fun findAll(pageable: Pageable): Page<ModelArtifactCatalogReadModel> = Page.empty(pageable)

        override fun findAllByCriteria(
            criteria: ModelArtifactCatalogReadModelCriteria?,
            pageable: Pageable
        ): Page<ModelArtifactCatalogReadModel> = Page.empty(pageable)

        override fun findById(id: UUID): ModelArtifactCatalogReadModel? = artifact?.takeIf { it.modelId == id }

        override fun findProjectionById(id: UUID): ModelArtifactCatalogReadModelProjection? = null

        override fun save(projection: ModelArtifactCatalogReadModelProjection) = Unit
    }

    private companion object {
        private val MODEL_ID: UUID = UUID.fromString("22222222-2222-4222-8222-222222222222")
        private const val DOWNLOAD_URI = "http://support:8080/api/files/22222222-2222-4222-8222-222222222222/content"
    }
}

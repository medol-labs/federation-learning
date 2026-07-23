package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModel
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.modelrepository.modelartifactcatalog.toReadModel

@Repository
class JpaModelArtifactCatalogReadModelRepository(private val jpaRepository: SpringDataModelArtifactCatalogReadModelRepository) : ModelArtifactCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<ModelArtifactCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): ModelArtifactCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): ModelArtifactCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: ModelArtifactCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun ModelArtifactCatalogReadModelEntity.toProjection(): ModelArtifactCatalogReadModelProjection =
        ModelArtifactCatalogReadModelProjection().also {
            it.modelVersionId = this@toProjection.modelVersionId
            it.modelArtifactRef = this@toProjection.modelArtifactRef
            it.modelRepositoryRef = this@toProjection.modelRepositoryRef
            it.modelFormat = this@toProjection.modelFormat
            it.modelHash = this@toProjection.modelHash
            it.modelSignatureRef = this@toProjection.modelSignatureRef
            it.modelSizeBytes = this@toProjection.modelSizeBytes
            it.sourceType = this@toProjection.sourceType
            it.trainingJobId = this@toProjection.trainingJobId
            it.roundId = this@toProjection.roundId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.state = this@toProjection.state
            it.registeredAt = this@toProjection.registeredAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun ModelArtifactCatalogReadModelProjection.toEntity(): ModelArtifactCatalogReadModelEntity =
        ModelArtifactCatalogReadModelEntity().also {
            it.modelVersionId = this@toEntity.modelVersionId
            it.modelArtifactRef = this@toEntity.modelArtifactRef
            it.modelRepositoryRef = this@toEntity.modelRepositoryRef
            it.modelFormat = this@toEntity.modelFormat
            it.modelHash = this@toEntity.modelHash
            it.modelSignatureRef = this@toEntity.modelSignatureRef
            it.modelSizeBytes = this@toEntity.modelSizeBytes
            it.sourceType = this@toEntity.sourceType
            it.trainingJobId = this@toEntity.trainingJobId
            it.roundId = this@toEntity.roundId
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.state = this@toEntity.state
            it.registeredAt = this@toEntity.registeredAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

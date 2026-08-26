package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModel
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelCriteria
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.modelrepository.modelartifactcatalog.toReadModel

@Repository
class JpaModelArtifactCatalogReadModelRepository(
    private val jpaRepository: SpringDataModelArtifactCatalogReadModelRepository,
    private val queryService: ModelArtifactCatalogReadModelQueryService
) : ModelArtifactCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<ModelArtifactCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: ModelArtifactCatalogReadModelCriteria?, pageable: Pageable): Page<ModelArtifactCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): ModelArtifactCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): ModelArtifactCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: ModelArtifactCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun ModelArtifactCatalogReadModelEntity.toProjection(): ModelArtifactCatalogReadModelProjection =
        ModelArtifactCatalogReadModelProjection().also {
            it.modelId = this@toProjection.modelId
            it.modelName = this@toProjection.modelName
            it.modelVersion = this@toProjection.modelVersion
            it.modelDescription = this@toProjection.modelDescription
            it.sourceType = this@toProjection.sourceType
            it.modelArtifactUri = this@toProjection.modelArtifactUri
            it.modelRegistryRef = this@toProjection.modelRegistryRef
            it.modelFormat = this@toProjection.modelFormat
            it.modelArtifactDigest = this@toProjection.modelArtifactDigest
            it.modelSignatureUri = this@toProjection.modelSignatureUri
            it.modelSizeBytes = this@toProjection.modelSizeBytes
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
            it.modelId = this@toEntity.modelId
            it.modelName = this@toEntity.modelName
            it.modelVersion = this@toEntity.modelVersion
            it.modelDescription = this@toEntity.modelDescription
            it.sourceType = this@toEntity.sourceType
            it.modelArtifactUri = this@toEntity.modelArtifactUri
            it.modelRegistryRef = this@toEntity.modelRegistryRef
            it.modelFormat = this@toEntity.modelFormat
            it.modelArtifactDigest = this@toEntity.modelArtifactDigest
            it.modelSignatureUri = this@toEntity.modelSignatureUri
            it.modelSizeBytes = this@toEntity.modelSizeBytes
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

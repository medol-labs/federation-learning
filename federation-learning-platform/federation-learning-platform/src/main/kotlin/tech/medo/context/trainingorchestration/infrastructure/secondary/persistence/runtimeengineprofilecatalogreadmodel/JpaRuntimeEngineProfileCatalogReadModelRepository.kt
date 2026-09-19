package tech.medo.trainingorchestration.infrastructure.secondary.persistence.runtimeengineprofilecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModel
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelCriteria
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelProjection
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelRepository
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.toReadModel

@Repository
class JpaRuntimeEngineProfileCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeEngineProfileCatalogReadModelRepository,
    private val queryService: RuntimeEngineProfileCatalogReadModelQueryService
) : RuntimeEngineProfileCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeEngineProfileCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeEngineProfileCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeEngineProfileCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeEngineProfileCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeEngineProfileCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeEngineProfileCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeEngineProfileCatalogReadModelEntity.toProjection(): RuntimeEngineProfileCatalogReadModelProjection =
        RuntimeEngineProfileCatalogReadModelProjection().also {
            it.runtimeEngineProfileId = this@toProjection.runtimeEngineProfileId
            it.profileName = this@toProjection.profileName
            it.pluginProfile = this@toProjection.pluginProfile
            it.runtimeEngineImage = this@toProjection.runtimeEngineImage
            it.imageDigest = this@toProjection.imageDigest
            it.supportedModelPluginsDescription = this@toProjection.supportedModelPluginsDescription
            it.supportedAggregationAlgorithmsDescription = this@toProjection.supportedAggregationAlgorithmsDescription
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.registeredAt = this@toProjection.registeredAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeEngineProfileCatalogReadModelProjection.toEntity(): RuntimeEngineProfileCatalogReadModelEntity =
        RuntimeEngineProfileCatalogReadModelEntity().also {
            it.runtimeEngineProfileId = this@toEntity.runtimeEngineProfileId
            it.profileName = this@toEntity.profileName
            it.pluginProfile = this@toEntity.pluginProfile
            it.runtimeEngineImage = this@toEntity.runtimeEngineImage
            it.imageDigest = this@toEntity.imageDigest
            it.supportedModelPluginsDescription = this@toEntity.supportedModelPluginsDescription
            it.supportedAggregationAlgorithmsDescription = this@toEntity.supportedAggregationAlgorithmsDescription
            it.active = this@toEntity.active
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

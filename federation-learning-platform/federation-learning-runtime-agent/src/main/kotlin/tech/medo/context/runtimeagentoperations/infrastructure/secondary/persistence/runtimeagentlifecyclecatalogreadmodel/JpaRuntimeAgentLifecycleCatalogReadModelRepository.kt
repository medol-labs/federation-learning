package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimeagentlifecyclecatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModel
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelRepository
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.toReadModel

@Repository
class JpaRuntimeAgentLifecycleCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeAgentLifecycleCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: RuntimeAgentLifecycleCatalogReadModelQueryService
) : RuntimeAgentLifecycleCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeAgentLifecycleCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeAgentLifecycleCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): RuntimeAgentLifecycleCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeAgentLifecycleCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeAgentLifecycleCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<RuntimeAgentLifecycleCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("runtimeAgentId"), id)
        }

    private fun accessSpecification(): Specification<RuntimeAgentLifecycleCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "RuntimeAgentOperations",
            readModelName = "RuntimeAgentLifecycleCatalog",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun RuntimeAgentLifecycleCatalogReadModelEntity.toProjection(): RuntimeAgentLifecycleCatalogReadModelProjection =
        RuntimeAgentLifecycleCatalogReadModelProjection().also {
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.agentVersion = this@toProjection.agentVersion
            it.runtimeAgentEndpoint = this@toProjection.runtimeAgentEndpoint
            it.endpointScope = this@toProjection.endpointScope
            it.lifecycleStatus = this@toProjection.lifecycleStatus
            it.bootstrapConfigurationLoaded = this@toProjection.bootstrapConfigurationLoaded
            it.bootstrapFailureReason = this@toProjection.bootstrapFailureReason
            it.runtimeAgentSelfCheckPassed = this@toProjection.runtimeAgentSelfCheckPassed
            it.configurationLoaded = this@toProjection.configurationLoaded
            it.secretStoreAccessible = this@toProjection.secretStoreAccessible
            it.runtimeEngineAdapterReady = this@toProjection.runtimeEngineAdapterReady
            it.modelRepositoryClientReady = this@toProjection.modelRepositoryClientReady
            it.localDatasetBindingStoreReady = this@toProjection.localDatasetBindingStoreReady
            it.workingDirectoryWritable = this@toProjection.workingDirectoryWritable
            it.bootstrappedAt = this@toProjection.bootstrappedAt
            it.bootstrapFailedAt = this@toProjection.bootstrapFailedAt
            it.startedAt = this@toProjection.startedAt
            it.readyAt = this@toProjection.readyAt
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeAgentLifecycleCatalogReadModelProjection.toEntity(): RuntimeAgentLifecycleCatalogReadModelEntity =
        RuntimeAgentLifecycleCatalogReadModelEntity().also {
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.agentVersion = this@toEntity.agentVersion
            it.runtimeAgentEndpoint = this@toEntity.runtimeAgentEndpoint
            it.endpointScope = this@toEntity.endpointScope
            it.lifecycleStatus = this@toEntity.lifecycleStatus
            it.bootstrapConfigurationLoaded = this@toEntity.bootstrapConfigurationLoaded
            it.bootstrapFailureReason = this@toEntity.bootstrapFailureReason
            it.runtimeAgentSelfCheckPassed = this@toEntity.runtimeAgentSelfCheckPassed
            it.configurationLoaded = this@toEntity.configurationLoaded
            it.secretStoreAccessible = this@toEntity.secretStoreAccessible
            it.runtimeEngineAdapterReady = this@toEntity.runtimeEngineAdapterReady
            it.modelRepositoryClientReady = this@toEntity.modelRepositoryClientReady
            it.localDatasetBindingStoreReady = this@toEntity.localDatasetBindingStoreReady
            it.workingDirectoryWritable = this@toEntity.workingDirectoryWritable
            it.bootstrappedAt = this@toEntity.bootstrappedAt
            it.bootstrapFailedAt = this@toEntity.bootstrapFailedAt
            it.startedAt = this@toEntity.startedAt
            it.readyAt = this@toEntity.readyAt
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

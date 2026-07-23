package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimeagentlifecyclecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModel
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelRepository
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.toReadModel

@Repository
class JpaRuntimeAgentLifecycleCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeAgentLifecycleCatalogReadModelRepository) : RuntimeAgentLifecycleCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeAgentLifecycleCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeAgentLifecycleCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeAgentLifecycleCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeAgentLifecycleCatalogReadModelEntity.toProjection(): RuntimeAgentLifecycleCatalogReadModelProjection =
        RuntimeAgentLifecycleCatalogReadModelProjection().also {
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.agentVersion = this@toProjection.agentVersion
            it.lifecycleStatus = this@toProjection.lifecycleStatus
            it.runtimeAgentSelfCheckPassed = this@toProjection.runtimeAgentSelfCheckPassed
            it.configurationLoaded = this@toProjection.configurationLoaded
            it.secretStoreAccessible = this@toProjection.secretStoreAccessible
            it.runtimeEngineAdapterReady = this@toProjection.runtimeEngineAdapterReady
            it.modelRepositoryClientReady = this@toProjection.modelRepositoryClientReady
            it.localDatasetBindingStoreReady = this@toProjection.localDatasetBindingStoreReady
            it.workingDirectoryWritable = this@toProjection.workingDirectoryWritable
            it.startedAt = this@toProjection.startedAt
            it.readyAt = this@toProjection.readyAt
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
            it.lifecycleStatus = this@toEntity.lifecycleStatus
            it.runtimeAgentSelfCheckPassed = this@toEntity.runtimeAgentSelfCheckPassed
            it.configurationLoaded = this@toEntity.configurationLoaded
            it.secretStoreAccessible = this@toEntity.secretStoreAccessible
            it.runtimeEngineAdapterReady = this@toEntity.runtimeEngineAdapterReady
            it.modelRepositoryClientReady = this@toEntity.modelRepositoryClientReady
            it.localDatasetBindingStoreReady = this@toEntity.localDatasetBindingStoreReady
            it.workingDirectoryWritable = this@toEntity.workingDirectoryWritable
            it.startedAt = this@toEntity.startedAt
            it.readyAt = this@toEntity.readyAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

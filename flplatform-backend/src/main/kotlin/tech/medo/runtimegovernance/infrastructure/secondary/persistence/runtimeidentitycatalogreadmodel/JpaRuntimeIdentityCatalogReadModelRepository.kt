package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModel
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelProjection
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import tech.medo.runtimegovernance.runtimeidentitycatalog.toReadModel

@Repository
class JpaRuntimeIdentityCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeIdentityCatalogReadModelRepository) : RuntimeIdentityCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeIdentityCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeIdentityCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeIdentityCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeIdentityCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeIdentityCatalogReadModelEntity.toProjection(): RuntimeIdentityCatalogReadModelProjection =
        RuntimeIdentityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.identityStatus = this@toProjection.identityStatus
            it.activatedAt = this@toProjection.activatedAt
            it.revokedAt = this@toProjection.revokedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeIdentityCatalogReadModelProjection.toEntity(): RuntimeIdentityCatalogReadModelEntity =
        RuntimeIdentityCatalogReadModelEntity().also {
            it.runtimeId = this@toEntity.runtimeId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.identityStatus = this@toEntity.identityStatus
            it.activatedAt = this@toEntity.activatedAt
            it.revokedAt = this@toEntity.revokedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

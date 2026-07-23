package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimecapabilitycatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModel
import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModelProjection
import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModelRepository
import tech.medo.runtimegovernance.runtimecapabilitycatalog.toReadModel

@Repository
class JpaRuntimeCapabilityCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeCapabilityCatalogReadModelRepository) : RuntimeCapabilityCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeCapabilityCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeCapabilityCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeCapabilityCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeCapabilityCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeCapabilityCatalogReadModelEntity.toProjection(): RuntimeCapabilityCatalogReadModelProjection =
        RuntimeCapabilityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.capabilityTypes = this@toProjection.capabilityTypes
            it.capabilityStatus = this@toProjection.capabilityStatus
            it.detectedAt = this@toProjection.detectedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeCapabilityCatalogReadModelProjection.toEntity(): RuntimeCapabilityCatalogReadModelEntity =
        RuntimeCapabilityCatalogReadModelEntity().also {
            it.runtimeId = this@toEntity.runtimeId
            it.capabilityTypes = this@toEntity.capabilityTypes
            it.capabilityStatus = this@toEntity.capabilityStatus
            it.detectedAt = this@toEntity.detectedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

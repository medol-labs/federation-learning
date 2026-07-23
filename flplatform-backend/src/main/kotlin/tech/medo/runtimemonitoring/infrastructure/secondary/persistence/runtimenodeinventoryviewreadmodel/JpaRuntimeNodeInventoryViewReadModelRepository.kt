package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenodeinventoryviewreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModel
import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModelProjection
import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModelRepository
import tech.medo.runtimemonitoring.runtimenodeinventoryview.toReadModel

@Repository
class JpaRuntimeNodeInventoryViewReadModelRepository(private val jpaRepository: SpringDataRuntimeNodeInventoryViewReadModelRepository) : RuntimeNodeInventoryViewReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeNodeInventoryViewReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeNodeInventoryViewReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeNodeInventoryViewReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeNodeInventoryViewReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeNodeInventoryViewReadModelEntity.toProjection(): RuntimeNodeInventoryViewReadModelProjection =
        RuntimeNodeInventoryViewReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeNodeInventoryReportId = this@toProjection.runtimeNodeInventoryReportId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.infrastructureNodeId = this@toProjection.infrastructureNodeId
            it.runtimeNodeRole = this@toProjection.runtimeNodeRole
            it.nodeReady = this@toProjection.nodeReady
            it.runtimeEngineVersion = this@toProjection.runtimeEngineVersion
            it.containerEngineVersion = this@toProjection.containerEngineVersion
            it.operatingSystem = this@toProjection.operatingSystem
            it.architecture = this@toProjection.architecture
            it.inventoryHash = this@toProjection.inventoryHash
            it.discoveredAt = this@toProjection.discoveredAt
            it.recordedAt = this@toProjection.recordedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeNodeInventoryViewReadModelProjection.toEntity(): RuntimeNodeInventoryViewReadModelEntity =
        RuntimeNodeInventoryViewReadModelEntity().also {
            it.nodeId = this@toEntity.nodeId
            it.runtimeNodeInventoryReportId = this@toEntity.runtimeNodeInventoryReportId
            it.organizationId = this@toEntity.organizationId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.runtimeNodeName = this@toEntity.runtimeNodeName
            it.infrastructureNodeId = this@toEntity.infrastructureNodeId
            it.runtimeNodeRole = this@toEntity.runtimeNodeRole
            it.nodeReady = this@toEntity.nodeReady
            it.runtimeEngineVersion = this@toEntity.runtimeEngineVersion
            it.containerEngineVersion = this@toEntity.containerEngineVersion
            it.operatingSystem = this@toEntity.operatingSystem
            it.architecture = this@toEntity.architecture
            it.inventoryHash = this@toEntity.inventoryHash
            it.discoveredAt = this@toEntity.discoveredAt
            it.recordedAt = this@toEntity.recordedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

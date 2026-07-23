package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModel
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelProjection
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelRepository
import tech.medo.organizationmanagement.organizationdirectory.toReadModel

@Repository
class JpaOrganizationDirectoryReadModelRepository(private val jpaRepository: SpringDataOrganizationDirectoryReadModelRepository) : OrganizationDirectoryReadModelRepository {
    override fun findAll(pageable: Pageable): Page<OrganizationDirectoryReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): OrganizationDirectoryReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): OrganizationDirectoryReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: OrganizationDirectoryReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun OrganizationDirectoryReadModelEntity.toProjection(): OrganizationDirectoryReadModelProjection =
        OrganizationDirectoryReadModelProjection().also {
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.organizationType = this@toProjection.organizationType
            it.state = this@toProjection.state
            it.approvedDatasetCount = this@toProjection.approvedDatasetCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun OrganizationDirectoryReadModelProjection.toEntity(): OrganizationDirectoryReadModelEntity =
        OrganizationDirectoryReadModelEntity().also {
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.organizationType = this@toEntity.organizationType
            it.state = this@toEntity.state
            it.approvedDatasetCount = this@toEntity.approvedDatasetCount
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

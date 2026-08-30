package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModelRepository
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Repository
class JpaPermissionCatalogReadModelRepository(
    private val jpaRepository: SpringDataPermissionCatalogReadModelRepository,
    private val queryService: PermissionCatalogReadModelQueryService
) : PermissionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<PermissionCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: PermissionCatalogReadModelCriteria?, pageable: Pageable): Page<PermissionCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): PermissionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): PermissionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: PermissionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun PermissionCatalogReadModelEntity.toProjection(): PermissionCatalogReadModelProjection =
        PermissionCatalogReadModelProjection().also {
            it.permissionId = this@toProjection.permissionId
            it.permissionCode = this@toProjection.permissionCode
            it.permissionName = this@toProjection.permissionName
            it.description = this@toProjection.description
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun PermissionCatalogReadModelProjection.toEntity(): PermissionCatalogReadModelEntity =
        PermissionCatalogReadModelEntity().also {
            it.permissionId = this@toEntity.permissionId
            it.permissionCode = this@toEntity.permissionCode
            it.permissionName = this@toEntity.permissionName
            it.description = this@toEntity.description
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

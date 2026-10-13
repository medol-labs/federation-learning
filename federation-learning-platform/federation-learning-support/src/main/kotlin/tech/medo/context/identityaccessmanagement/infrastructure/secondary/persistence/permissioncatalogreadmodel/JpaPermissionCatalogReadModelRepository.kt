package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.identityaccessmanagement.permissioncatalogs.PermissionCatalogReadModel
import tech.medo.identityaccessmanagement.permissioncatalogs.PermissionCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.permissioncatalogs.PermissionCatalogReadModelProjection
import tech.medo.identityaccessmanagement.permissioncatalogs.PermissionCatalogReadModelRepository
import tech.medo.identityaccessmanagement.permissioncatalogs.toReadModel

@Repository
class JpaPermissionCatalogReadModelRepository(
    private val jpaRepository: SpringDataPermissionCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: PermissionCatalogReadModelQueryService
) : PermissionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<PermissionCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: PermissionCatalogReadModelCriteria?, pageable: Pageable): Page<PermissionCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): PermissionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): PermissionCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): PermissionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: PermissionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<PermissionCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("permissionId"), id)
        }

    private fun accessSpecification(): Specification<PermissionCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "IdentityAccessManagement",
            readModelName = "PermissionCatalog",
            policy = ReadModelAccessPolicy(
            name = "IdentityAccessManagementPublicAccess",
            fields = emptyList<String>()
        )
        )

    private fun PermissionCatalogReadModelEntity.toProjection(): PermissionCatalogReadModelProjection =
        PermissionCatalogReadModelProjection().also {
            it.permissionId = this@toProjection.permissionId
            it.permissionCode = this@toProjection.permissionCode
            it.permissionName = this@toProjection.permissionName
            it.description = this@toProjection.description
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
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
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

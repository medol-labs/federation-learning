package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolepermissiongrantcatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModel
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelKey
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelProjection
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelRepository
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.toReadModel

@Repository
class JpaRolePermissionGrantCatalogReadModelRepository(
    private val jpaRepository: SpringDataRolePermissionGrantCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: RolePermissionGrantCatalogReadModelQueryService
) : RolePermissionGrantCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RolePermissionGrantCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RolePermissionGrantCatalogReadModelCriteria?, pageable: Pageable): Page<RolePermissionGrantCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun findProjectionsByRoleCode(roleCode: String): List<RolePermissionGrantCatalogReadModelProjection> =
        jpaRepository.findAllByRoleCode(roleCode).map { it.toProjection() }

    override fun findProjectionsByPermissionCode(permissionCode: String): List<RolePermissionGrantCatalogReadModelProjection> =
        jpaRepository.findAllByPermissionCode(permissionCode).map { it.toProjection() }

    override fun save(projection: RolePermissionGrantCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: RolePermissionGrantCatalogReadModelKey): Specification<RolePermissionGrantCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.and(
                criteriaBuilder.equal(root.get<String>("roleCode"), id.roleCode),
                criteriaBuilder.equal(root.get<String>("permissionCode"), id.permissionCode)
            )
        }

    private fun accessSpecification(): Specification<RolePermissionGrantCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "IdentityAccessManagement",
            readModelName = "RolePermissionGrantCatalog",
            policy = ReadModelAccessPolicy(
            name = "IdentityAccessManagementPublicAccess",
            fields = emptyList<String>()
        )
        )

    private fun RolePermissionGrantCatalogReadModelEntity.toProjection(): RolePermissionGrantCatalogReadModelProjection =
        RolePermissionGrantCatalogReadModelProjection().also {
            it.roleId = this@toProjection.roleId
            it.roleCode = this@toProjection.roleCode
            it.roleName = this@toProjection.roleName
            it.permissionCode = this@toProjection.permissionCode
            it.permissionName = this@toProjection.permissionName
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RolePermissionGrantCatalogReadModelProjection.toEntity(): RolePermissionGrantCatalogReadModelEntity =
        RolePermissionGrantCatalogReadModelEntity().also {
            it.roleId = this@toEntity.roleId
            it.roleCode = this@toEntity.roleCode
            it.roleName = this@toEntity.roleName
            it.permissionCode = this@toEntity.permissionCode
            it.permissionName = this@toEntity.permissionName
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

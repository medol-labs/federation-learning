package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.userroleassignmentcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModel
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelKey
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelProjection
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelRepository
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.toReadModel

@Repository
class JpaUserRoleAssignmentCatalogReadModelRepository(
    private val jpaRepository: SpringDataUserRoleAssignmentCatalogReadModelRepository,
    private val queryService: UserRoleAssignmentCatalogReadModelQueryService
) : UserRoleAssignmentCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: UserRoleAssignmentCatalogReadModelCriteria?, pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun findProjectionsByUserAccountId(userAccountId: UUID): List<UserRoleAssignmentCatalogReadModelProjection> =
        jpaRepository.findAllByUserAccountId(userAccountId).map { it.toProjection() }

    override fun findProjectionsByRoleCode(roleCode: String): List<UserRoleAssignmentCatalogReadModelProjection> =
        jpaRepository.findAllByRoleCode(roleCode).map { it.toProjection() }

    override fun save(projection: UserRoleAssignmentCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun UserRoleAssignmentCatalogReadModelEntity.toProjection(): UserRoleAssignmentCatalogReadModelProjection =
        UserRoleAssignmentCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.roleCode = this@toProjection.roleCode
            it.roleName = this@toProjection.roleName
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun UserRoleAssignmentCatalogReadModelProjection.toEntity(): UserRoleAssignmentCatalogReadModelEntity =
        UserRoleAssignmentCatalogReadModelEntity().also {
            it.userAccountId = this@toEntity.userAccountId
            it.username = this@toEntity.username
            it.roleCode = this@toEntity.roleCode
            it.roleName = this@toEntity.roleName
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

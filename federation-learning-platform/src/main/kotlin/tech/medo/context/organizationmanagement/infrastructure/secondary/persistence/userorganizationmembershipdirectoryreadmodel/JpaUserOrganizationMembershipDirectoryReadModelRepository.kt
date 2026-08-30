package tech.medo.organizationmanagement.infrastructure.secondary.persistence.userorganizationmembershipdirectoryreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum;

import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModel
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModelCriteria
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModelProjection
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModelRepository
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.toReadModel

@Repository
class JpaUserOrganizationMembershipDirectoryReadModelRepository(
    private val jpaRepository: SpringDataUserOrganizationMembershipDirectoryReadModelRepository,
    private val queryService: UserOrganizationMembershipDirectoryReadModelQueryService
) : UserOrganizationMembershipDirectoryReadModelRepository {
    override fun findAll(pageable: Pageable): Page<UserOrganizationMembershipDirectoryReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: UserOrganizationMembershipDirectoryReadModelCriteria?, pageable: Pageable): Page<UserOrganizationMembershipDirectoryReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): UserOrganizationMembershipDirectoryReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): UserOrganizationMembershipDirectoryReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: UserOrganizationMembershipDirectoryReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun UserOrganizationMembershipDirectoryReadModelEntity.toProjection(): UserOrganizationMembershipDirectoryReadModelProjection =
        UserOrganizationMembershipDirectoryReadModelProjection().also {
            it.userOrganizationMembershipId = this@toProjection.userOrganizationMembershipId
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.organizationUserRole = this@toProjection.organizationUserRole
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun UserOrganizationMembershipDirectoryReadModelProjection.toEntity(): UserOrganizationMembershipDirectoryReadModelEntity =
        UserOrganizationMembershipDirectoryReadModelEntity().also {
            it.userOrganizationMembershipId = this@toEntity.userOrganizationMembershipId
            it.userAccountId = this@toEntity.userAccountId
            it.username = this@toEntity.username
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.organizationUserRole = this@toEntity.organizationUserRole
            it.state = this@toEntity.state
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }

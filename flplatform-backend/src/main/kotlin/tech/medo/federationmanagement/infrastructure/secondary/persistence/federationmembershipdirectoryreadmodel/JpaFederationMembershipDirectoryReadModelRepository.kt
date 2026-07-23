package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModel
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelProjection
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelRepository
import tech.medo.federationmanagement.federationmembershipdirectory.toReadModel

@Repository
class JpaFederationMembershipDirectoryReadModelRepository(private val jpaRepository: SpringDataFederationMembershipDirectoryReadModelRepository) : FederationMembershipDirectoryReadModelRepository {
    override fun findAll(pageable: Pageable): Page<FederationMembershipDirectoryReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun findProjectionsByFederationId(federationId: UUID): List<FederationMembershipDirectoryReadModelProjection> =
        jpaRepository.findAllByFederationId(federationId).map { it.toProjection() }

    override fun findProjectionsByOrganizationId(organizationId: UUID): List<FederationMembershipDirectoryReadModelProjection> =
        jpaRepository.findAllByOrganizationId(organizationId).map { it.toProjection() }

    override fun save(projection: FederationMembershipDirectoryReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun FederationMembershipDirectoryReadModelEntity.toProjection(): FederationMembershipDirectoryReadModelProjection =
        FederationMembershipDirectoryReadModelProjection().also {
            it.federationId = this@toProjection.federationId
            it.organizationId = this@toProjection.organizationId
            it.federationName = this@toProjection.federationName
            it.organizationName = this@toProjection.organizationName
            it.membershipStatus = this@toProjection.membershipStatus
            it.invitationNote = this@toProjection.invitationNote
            it.approvalNote = this@toProjection.approvalNote
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun FederationMembershipDirectoryReadModelProjection.toEntity(): FederationMembershipDirectoryReadModelEntity =
        FederationMembershipDirectoryReadModelEntity().also {
            it.federationId = this@toEntity.federationId
            it.organizationId = this@toEntity.organizationId
            it.federationName = this@toEntity.federationName
            it.organizationName = this@toEntity.organizationName
            it.membershipStatus = this@toEntity.membershipStatus
            it.invitationNote = this@toEntity.invitationNote
            it.approvalNote = this@toEntity.approvalNote
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

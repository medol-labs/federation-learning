package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationoverviewreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModel
import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelProjection
import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelRepository
import tech.medo.federationmanagement.federationoverview.toReadModel

@Repository
class JpaFederationOverviewReadModelRepository(private val jpaRepository: SpringDataFederationOverviewReadModelRepository) : FederationOverviewReadModelRepository {
    override fun findAll(pageable: Pageable): Page<FederationOverviewReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): FederationOverviewReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): FederationOverviewReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: FederationOverviewReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun FederationOverviewReadModelEntity.toProjection(): FederationOverviewReadModelProjection =
        FederationOverviewReadModelProjection().also {
            it.federationId = this@toProjection.federationId
            it.federationName = this@toProjection.federationName
            it.state = this@toProjection.state
            it.minimumParticipantCount = this@toProjection.minimumParticipantCount
            it.activeMemberCount = this@toProjection.activeMemberCount
            it.pendingInvitationCount = this@toProjection.pendingInvitationCount
            it.activeRuntimeCount = this@toProjection.activeRuntimeCount
            it.activeTrainingJobCount = this@toProjection.activeTrainingJobCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun FederationOverviewReadModelProjection.toEntity(): FederationOverviewReadModelEntity =
        FederationOverviewReadModelEntity().also {
            it.federationId = this@toEntity.federationId
            it.federationName = this@toEntity.federationName
            it.state = this@toEntity.state
            it.minimumParticipantCount = this@toEntity.minimumParticipantCount
            it.activeMemberCount = this@toEntity.activeMemberCount
            it.pendingInvitationCount = this@toEntity.pendingInvitationCount
            it.activeRuntimeCount = this@toEntity.activeRuntimeCount
            it.activeTrainingJobCount = this@toEntity.activeTrainingJobCount
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}

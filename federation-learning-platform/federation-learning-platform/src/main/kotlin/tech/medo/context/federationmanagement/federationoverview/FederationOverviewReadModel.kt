package tech.medo.federationmanagement.federationoverview

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.federationmanagement.domain.states.FederationStateEnum;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.StringFilter


class FederationOverviewReadModelQuery

class FederationOverviewReadModelCriteria {
    var federationId: StringFilter? = null
    var federationName: StringFilter? = null
    var state: Filter<FederationStateEnum>? = null
    var minimumParticipantCount: IntegerFilter? = null
    var activeMemberCount: IntegerFilter? = null
    var pendingInvitationCount: IntegerFilter? = null
    var activeRuntimeCount: IntegerFilter? = null
    var activeTrainingJobCount: IntegerFilter? = null
}


class FederationOverviewReadModelProjection : MetadataProjection {
    var federationId: UUID? = null
    var federationName: String? = null
    var state: FederationStateEnum? = null
    var minimumParticipantCount: Int? = null
    var activeMemberCount: Int? = null
    var pendingInvitationCount: Int? = null
    var activeRuntimeCount: Int? = null
    var activeTrainingJobCount: Int? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun FederationOverviewReadModelProjection.toReadModel(): FederationOverviewReadModel =
    FederationOverviewReadModel(
    federationId = federationId,
    federationName = federationName,
    state = state,
    minimumParticipantCount = minimumParticipantCount,
    activeMemberCount = activeMemberCount,
    pendingInvitationCount = pendingInvitationCount,
    activeRuntimeCount = activeRuntimeCount,
    activeTrainingJobCount = activeTrainingJobCount,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface FederationOverviewReadModelRepository {
    fun findAll(pageable: Pageable): Page<FederationOverviewReadModel>
    fun findAllByCriteria(criteria: FederationOverviewReadModelCriteria?, pageable: Pageable): Page<FederationOverviewReadModel>
    fun findById(id: UUID): FederationOverviewReadModel?
    fun findProjectionById(id: UUID): FederationOverviewReadModelProjection?
    fun save(projection: FederationOverviewReadModelProjection)
}

data class FederationOverviewReadModel(
    val federationId: UUID?,
    val federationName: String?,
    val state: FederationStateEnum?,
    val minimumParticipantCount: Int?,
    val activeMemberCount: Int?,
    val pendingInvitationCount: Int?,
    val activeRuntimeCount: Int?,
    val activeTrainingJobCount: Int?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)

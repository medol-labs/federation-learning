package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationoverviewreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.federationmanagement.domain.states.FederationStateEnum;


@Entity
class FederationOverviewReadModelEntity : MetadataProjection {
    @Id
    var federationId: UUID? = null
    var federationName: String? = null
    @Enumerated(EnumType.STRING)
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

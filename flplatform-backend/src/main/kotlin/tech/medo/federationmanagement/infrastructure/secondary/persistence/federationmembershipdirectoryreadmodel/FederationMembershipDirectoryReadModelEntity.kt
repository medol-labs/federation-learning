package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey
import java.util.UUID;


@IdClass(FederationMembershipDirectoryReadModelKey::class)
@Entity
class FederationMembershipDirectoryReadModelEntity : MetadataProjection {
    @Id
    var federationId: UUID? = null
    @Id
    var organizationId: UUID? = null
    var federationName: String? = null
    var organizationName: String? = null
    var membershipStatus: String? = null
    var invitationNote: String? = null
    var approvalNote: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

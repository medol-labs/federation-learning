package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey
import java.util.UUID
import java.time.LocalDateTime

@IdClass(FederationMembershipDirectoryReadModelKey::class)
@Entity
@Table(name = "federation_membership_directory")
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
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}

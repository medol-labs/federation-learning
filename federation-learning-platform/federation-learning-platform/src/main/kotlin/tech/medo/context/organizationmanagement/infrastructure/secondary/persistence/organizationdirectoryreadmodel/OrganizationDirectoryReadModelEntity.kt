package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.organizationmanagement.domain.types.OrganizationType
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum
import java.time.LocalDateTime

@Entity
@Table(name = "organization_directory")
class OrganizationDirectoryReadModelEntity : MetadataProjection {
    @Id
    var organizationId: UUID? = null
    var organizationName: String? = null
    @Enumerated(EnumType.STRING)
    var organizationType: OrganizationType? = null
    @Enumerated(EnumType.STRING)
    var state: OrganizationStateEnum? = null
    var approvedDatasetCount: Int? = null
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

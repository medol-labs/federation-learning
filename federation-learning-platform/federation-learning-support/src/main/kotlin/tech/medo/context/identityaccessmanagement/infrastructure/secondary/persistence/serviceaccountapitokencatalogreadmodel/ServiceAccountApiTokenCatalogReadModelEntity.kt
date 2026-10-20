package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.serviceaccountapitokencatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime

@Entity
@Table(name = "service_account_api_token_catalog")
class ServiceAccountApiTokenCatalogReadModelEntity : MetadataProjection {
    @Id
    var apiTokenId: UUID? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var tokenName: String? = null
    var tokenPrefix: String? = null
    var issuedAt: String? = null
    @Column(columnDefinition = "text")
    var roles: String? = null
    @Column(columnDefinition = "text")
    var permissions: String? = null
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

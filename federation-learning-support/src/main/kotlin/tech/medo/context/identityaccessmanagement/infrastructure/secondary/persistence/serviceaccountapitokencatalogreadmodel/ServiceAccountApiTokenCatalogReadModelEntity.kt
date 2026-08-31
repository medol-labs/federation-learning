package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.serviceaccountapitokencatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

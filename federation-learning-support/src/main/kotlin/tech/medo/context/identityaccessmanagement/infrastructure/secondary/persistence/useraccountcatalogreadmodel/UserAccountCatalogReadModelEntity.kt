package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
class UserAccountCatalogReadModelEntity : MetadataProjection {
    @Id
    var userAccountId: UUID? = null
    var username: String? = null
    var providerSubject: String? = null
    var organizationId: UUID? = null
    var active: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

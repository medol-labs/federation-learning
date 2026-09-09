package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
@Table(name = "user_account_catalog")
class UserAccountCatalogReadModelEntity : MetadataProjection {
    @Id
    var userAccountId: UUID? = null
    var username: String? = null
    var providerSubject: String? = null
    var userSource: String? = null
    var passwordHash: String? = null
    var active: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.userroleassignmentcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelKey
import java.util.UUID;


@IdClass(UserRoleAssignmentCatalogReadModelKey::class)
@Entity
class UserRoleAssignmentCatalogReadModelEntity : MetadataProjection {
    @Id
    var userAccountId: UUID? = null
    var username: String? = null
    @Id
    var roleCode: String? = null
    var roleName: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolepermissiongrantcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelKey
import java.util.UUID;


@IdClass(RolePermissionGrantCatalogReadModelKey::class)
@Entity
class RolePermissionGrantCatalogReadModelEntity : MetadataProjection {
    var roleId: UUID? = null
    @Id
    var roleCode: String? = null
    var roleName: String? = null
    @Id
    var permissionCode: String? = null
    var permissionName: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

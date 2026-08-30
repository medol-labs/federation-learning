package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
class PermissionCatalogReadModelEntity : MetadataProjection {
    @Id
    var permissionId: UUID? = null
    var permissionCode: String? = null
    var permissionName: String? = null
    @Column(columnDefinition = "text")
    var description: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

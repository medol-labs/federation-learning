package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum;


@Entity
class OrganizationDirectoryReadModelEntity : MetadataProjection {
    @Id
    var organizationId: UUID? = null
    var organizationName: String? = null
    @Enumerated(EnumType.STRING)
    var organizationType: OrganizationType? = null
    @Enumerated(EnumType.STRING)
    var state: OrganizationStateEnum? = null
    var approvedDatasetCount: Int? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

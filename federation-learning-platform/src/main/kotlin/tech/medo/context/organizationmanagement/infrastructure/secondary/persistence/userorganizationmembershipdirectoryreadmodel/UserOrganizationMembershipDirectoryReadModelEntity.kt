package tech.medo.organizationmanagement.infrastructure.secondary.persistence.userorganizationmembershipdirectoryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum;


@Entity
class UserOrganizationMembershipDirectoryReadModelEntity : MetadataProjection {
    @Id
    var userOrganizationMembershipId: UUID? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationUserRole: String? = null
    @Enumerated(EnumType.STRING)
    var state: UserOrganizationMembershipStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

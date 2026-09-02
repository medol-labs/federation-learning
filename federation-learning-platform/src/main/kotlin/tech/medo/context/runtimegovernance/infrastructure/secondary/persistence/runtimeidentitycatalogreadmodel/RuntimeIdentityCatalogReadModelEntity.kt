package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "runtime_identity_catalog")
class RuntimeIdentityCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var identityStatus: String? = null
    var activatedAt: LocalDateTime? = null
    var revokedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

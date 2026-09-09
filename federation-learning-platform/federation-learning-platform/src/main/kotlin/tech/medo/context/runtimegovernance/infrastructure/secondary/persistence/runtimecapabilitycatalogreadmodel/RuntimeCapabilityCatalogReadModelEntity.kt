package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimecapabilitycatalogreadmodel

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
@Table(name = "runtime_capability_catalog")
class RuntimeCapabilityCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeId: UUID? = null
    @Column(columnDefinition = "text")
    var capabilityTypes: String? = null
    var capabilityStatus: String? = null
    var detectedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

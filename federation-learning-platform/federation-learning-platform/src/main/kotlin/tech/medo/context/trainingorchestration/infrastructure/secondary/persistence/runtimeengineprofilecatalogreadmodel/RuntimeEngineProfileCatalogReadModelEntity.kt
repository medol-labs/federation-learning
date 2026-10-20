package tech.medo.trainingorchestration.infrastructure.secondary.persistence.runtimeengineprofilecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "runtime_engine_profile_catalog")
class RuntimeEngineProfileCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeEngineProfileId: UUID? = null
    var profileName: String? = null
    var pluginProfile: String? = null
    var runtimeEngineImage: String? = null
    var imageDigest: String? = null
    @Column(columnDefinition = "text")
    var supportedModelPluginsDescription: String? = null
    @Column(columnDefinition = "text")
    var supportedAggregationAlgorithmsDescription: String? = null
    var active: Boolean? = null
    @Enumerated(EnumType.STRING)
    var state: RuntimeEngineProfileStateEnum? = null
    var registeredAt: LocalDateTime? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}

package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "runtime_dataset_binding_catalog")
class RuntimeDatasetBindingCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var organizationName: String? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var runtimeName: String? = null
    var filePath: String? = null
    var dataFormat: String? = null
    var configuredAt: LocalDateTime? = null
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

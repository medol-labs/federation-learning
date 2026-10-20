package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.trainingalertcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "training_alert_catalog")
class TrainingAlertCatalogReadModelEntity : MetadataProjection {
    @Id
    var alertId: UUID? = null
    var nodeId: UUID? = null
    var trainingJobId: UUID? = null
    var runtimeNodeName: String? = null
    var trainingJobObjective: String? = null
    var severity: String? = null
    @Column(columnDefinition = "text")
    var message: String? = null
    @Enumerated(EnumType.STRING)
    var state: TrainingAlertStateEnum? = null
    var acknowledgedAt: LocalDateTime? = null
    var resolvedAt: LocalDateTime? = null
    var resolutionSummary: String? = null
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

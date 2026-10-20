package tech.medo.runtimemonitoring.trainingalertcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class TrainingAlertCatalogReadModelQuery

class TrainingAlertCatalogReadModelCriteria {
    var alertId: StringFilter? = null
    var nodeId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var runtimeNodeName: StringFilter? = null
    var trainingJobObjective: StringFilter? = null
    var severity: StringFilter? = null
    var message: StringFilter? = null
    var state: Filter<TrainingAlertStateEnum>? = null
    var acknowledgedAt: RangeFilter<LocalDateTime>? = null
    var resolvedAt: RangeFilter<LocalDateTime>? = null
    var resolutionSummary: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class TrainingAlertCatalogReadModelProjection : MetadataProjection {
    var alertId: UUID? = null
    var nodeId: UUID? = null
    var trainingJobId: UUID? = null
    var runtimeNodeName: String? = null
    var trainingJobObjective: String? = null
    var severity: String? = null
    var message: String? = null
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

fun TrainingAlertCatalogReadModelProjection.toReadModel(): TrainingAlertCatalogReadModel =
    TrainingAlertCatalogReadModel(
    alertId = alertId,
    nodeId = nodeId,
    trainingJobId = trainingJobId,
    runtimeNodeName = runtimeNodeName,
    trainingJobObjective = trainingJobObjective,
    severity = severity,
    message = message,
    state = state,
    acknowledgedAt = acknowledgedAt,
    resolvedAt = resolvedAt,
    resolutionSummary = resolutionSummary,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingAlertCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingAlertCatalogReadModel>
    fun findAllByCriteria(criteria: TrainingAlertCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingAlertCatalogReadModel>
    fun findById(id: UUID): TrainingAlertCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): TrainingAlertCatalogReadModel?
    fun findProjectionById(id: UUID): TrainingAlertCatalogReadModelProjection?
    fun save(projection: TrainingAlertCatalogReadModelProjection)
}

data class TrainingAlertCatalogReadModel(
    val alertId: UUID?,
    val nodeId: UUID?,
    val trainingJobId: UUID?,
    val runtimeNodeName: String?,
    val trainingJobObjective: String?,
    val severity: String?,
    val message: String?,
    val state: TrainingAlertStateEnum?,
    val acknowledgedAt: LocalDateTime?,
    val resolvedAt: LocalDateTime?,
    val resolutionSummary: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)

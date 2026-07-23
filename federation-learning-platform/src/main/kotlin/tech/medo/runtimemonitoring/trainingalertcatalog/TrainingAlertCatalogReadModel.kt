package tech.medo.runtimemonitoring.trainingalertcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class TrainingAlertCatalogReadModelQuery

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
    var canAcknowledge: Boolean? = null
    var canResolve: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
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
    canAcknowledge = canAcknowledge,
    canResolve = canResolve,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingAlertCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingAlertCatalogReadModel>
    fun findById(id: UUID): TrainingAlertCatalogReadModel?
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
    val canAcknowledge: Boolean?,
    val canResolve: Boolean?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)

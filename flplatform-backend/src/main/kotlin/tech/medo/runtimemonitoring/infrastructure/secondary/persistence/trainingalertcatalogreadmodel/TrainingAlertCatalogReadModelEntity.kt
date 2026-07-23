package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.trainingalertcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class TrainingAlertCatalogReadModelEntity : MetadataProjection {
    @Id
    var alertId: UUID? = null
    var nodeId: UUID? = null
    var trainingJobId: UUID? = null
    var runtimeNodeName: String? = null
    var trainingJobObjective: String? = null
    var severity: String? = null
    var message: String? = null
    @Enumerated(EnumType.STRING)
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

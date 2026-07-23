package tech.medo.modellifecycle.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelEvaluationPackageRecordedEvent(
    @EventTag(key = "modelVersionId")
    val modelVersionId: UUID,
    val trainingJobId: UUID,
    val evaluationReportId: UUID,
    val experimentId: UUID,
    val hyperparameterSnapshotId: UUID,
    val reproducibilityManifestId: UUID,
    val modelCardId: UUID,
    val baselineModelVersionId: UUID?
)

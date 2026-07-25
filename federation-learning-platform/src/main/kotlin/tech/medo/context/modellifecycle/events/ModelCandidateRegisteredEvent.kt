package tech.medo.modellifecycle.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class ModelCandidateRegisteredEvent(
    @EventTag(key = "modelVersionId")
    val modelVersionId: UUID,
    val trainingJobId: UUID,
    val finalRoundId: UUID,
    val modelArtifactId: UUID,
    val modelHash: String,
    val evaluationReportId: UUID,
    val finalGlobalAccuracy: BigDecimal
)

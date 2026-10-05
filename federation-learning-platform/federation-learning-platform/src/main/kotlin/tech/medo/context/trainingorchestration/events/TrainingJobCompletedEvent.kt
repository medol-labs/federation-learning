package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class TrainingJobCompletedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val finalRoundId: UUID,
    val finalModelId: UUID,
    val finalModelArtifactDigest: String,
    val finalGlobalAccuracy: BigDecimal,
    val finalEvaluationReportId: UUID,
    val trainingJobObjective: String,
    val stopReason: String
)

package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class HistoricalRoundUpdateDispositionRecordedEvent(
    val modelUpdateSubmissionId: UUID,
    val trainingJobId: UUID,
    @EventTag(key = "roundId")
    val roundId: UUID,
    val canonicalRoundId: UUID,
    val runtimeId: UUID,
    val updateArtifactId: UUID,
    val artifactDigest: String,
    val recoveryDigest: String,
    val reconciledRejectionIds: List<UUID>
)

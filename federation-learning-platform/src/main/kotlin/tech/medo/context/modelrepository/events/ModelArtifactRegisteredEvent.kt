package tech.medo.modelrepository.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelArtifactRegisteredEvent(
    @EventTag(key = "modelVersionId")
    val modelVersionId: UUID,
    val modelArtifactRef: String,
    val modelRepositoryRef: String,
    val modelFormat: String,
    val modelHash: String,
    val modelSignatureRef: String?,
    val modelSizeBytes: Int?,
    val sourceType: String
)

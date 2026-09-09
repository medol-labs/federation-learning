package tech.medo.modelrepository.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelArtifactDownloadAuthorizedEvent(
    val modelId: UUID,
    @EventTag(key = "modelName")
    val modelName: String,
    @EventTag(key = "modelVersion")
    val modelVersion: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val downloadUri: String
)

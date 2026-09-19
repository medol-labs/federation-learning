package tech.medo.modelrepository.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ModelArtifactRegisteredEvent(
    val modelId: UUID,
    @EventTag(key = "modelName")
    val modelName: String,
    val modelPlugin: String,
    @EventTag(key = "modelVersion")
    val modelVersion: String,
    val modelDescription: String?,
    val sourceType: String,
    val modelArtifactUri: String,
    val modelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val modelSignatureUri: String?,
    val modelSizeBytes: Int?
)

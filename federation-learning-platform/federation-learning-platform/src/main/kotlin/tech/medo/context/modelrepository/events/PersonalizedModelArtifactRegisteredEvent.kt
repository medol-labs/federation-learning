package tech.medo.modelrepository.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class PersonalizedModelArtifactRegisteredEvent(
    val modelId: UUID,
    @EventTag(key = "modelName")
    val modelName: String,
    val modelPlugin: String,
    @EventTag(key = "modelVersion")
    val modelVersion: String,
    val modelDescription: String?,
    val sourceType: String,
    val modelScope: String,
    val modelArtifactUri: String,
    val modelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val modelSignatureUri: String?,
    val modelSizeBytes: Int?,
    val trainingJobId: UUID,
    val trainingJobObjective: String?,
    val roundId: UUID,
    val personalizationMode: String?,
    val personalizationTargetType: String?,
    val personalizationTargetId: UUID?,
    val personalizationCohortId: UUID?,
    val baseGlobalModelId: UUID?
)

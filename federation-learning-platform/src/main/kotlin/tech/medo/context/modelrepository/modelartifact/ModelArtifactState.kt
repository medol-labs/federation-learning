package tech.medo.modelrepository.modelartifact

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum

import java.util.UUID;


@EventSourced(idType = ModelArtifactSelection::class)
class ModelArtifactState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: ModelArtifactSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(ModelArtifactTags.MODEL_NAME, selection.modelName.toString())),
                EventCriteria.havingTags(Tag.of(ModelArtifactTags.MODEL_VERSION, selection.modelVersion.toString()))
        )
    }


    var currentState: ModelArtifactStateEnum? = null
    var modelId: UUID? = null
    var modelName: String? = null
    var modelVersion: String? = null
    var sourceType: String? = null
    var modelArtifactUri: String? = null
    var modelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var modelSignatureUri: String? = null
    var modelSizeBytes: Int? = null

    @EventSourcingHandler
    fun evolve(event: ModelArtifactRegisteredEvent): ModelArtifactState = apply {
        currentState = ModelArtifactStateEnum.REGISTERED
        modelId = event.modelId
        modelName = event.modelName
        modelVersion = event.modelVersion
        sourceType = event.sourceType
        modelArtifactUri = event.modelArtifactUri
        modelRegistryRef = event.modelRegistryRef
        modelFormat = event.modelFormat
        modelArtifactDigest = event.modelArtifactDigest
        modelSignatureUri = event.modelSignatureUri
        modelSizeBytes = event.modelSizeBytes
    }
}

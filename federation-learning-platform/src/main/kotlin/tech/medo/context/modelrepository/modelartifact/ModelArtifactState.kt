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


@EventSourced(idType = UUID::class, tagKey = ModelArtifactTags.MODEL_VERSION_ID)
class ModelArtifactState @EntityCreator constructor() {

    var currentState: ModelArtifactStateEnum? = null
    private var modelVersionId: UUID? = null
    private var modelArtifactRef: String? = null
    private var modelRepositoryRef: String? = null
    private var modelFormat: String? = null
    private var modelHash: String? = null
    private var modelSignatureRef: String? = null
    private var modelSizeBytes: Int? = null
    private var sourceType: String? = null

    @EventSourcingHandler
    fun evolve(event: ModelArtifactRegisteredEvent): ModelArtifactState = apply {
        currentState = ModelArtifactStateEnum.REGISTERED
        modelVersionId = event.modelVersionId
        modelArtifactRef = event.modelArtifactRef
        modelRepositoryRef = event.modelRepositoryRef
        modelFormat = event.modelFormat
        modelHash = event.modelHash
        modelSignatureRef = event.modelSignatureRef
        modelSizeBytes = event.modelSizeBytes
        sourceType = event.sourceType
    }
}

package tech.medo.trainingorchestration.runtimeengineprofile

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.RuntimeEngineProfileRegisteredEvent
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum

import java.util.UUID;


@EventSourced(idType = String::class, tagKey = RuntimeEngineProfileTags.PROFILE_NAME)
class RuntimeEngineProfileState @EntityCreator constructor() {

    var currentState: RuntimeEngineProfileStateEnum? = null
    var runtimeEngineProfileId: UUID? = null
    var profileName: String? = null
    var pluginProfile: String? = null
    var runtimeEngineImage: String? = null
    var imageDigest: String? = null
    var supportedModelPluginsDescription: String? = null
    var supportedAggregationAlgorithmsDescription: String? = null
    var active: Boolean? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeEngineProfileRegisteredEvent): RuntimeEngineProfileState = apply {
        currentState = RuntimeEngineProfileStateEnum.REGISTERED
        runtimeEngineProfileId = event.runtimeEngineProfileId
        profileName = event.profileName
        pluginProfile = event.pluginProfile
        runtimeEngineImage = event.runtimeEngineImage
        imageDigest = event.imageDigest
        supportedModelPluginsDescription = event.supportedModelPluginsDescription
        supportedAggregationAlgorithmsDescription = event.supportedAggregationAlgorithmsDescription
        active = event.active
    }
}

package tech.medo.runtimegovernance.runtimecapability

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent
import tech.medo.runtimegovernance.domain.states.RuntimeCapabilityStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeCapabilityTags.RUNTIME_ID)
class RuntimeCapabilityState @EntityCreator constructor() {

    var currentState: RuntimeCapabilityStateEnum? = null
    var runtimeId: UUID? = null
    var capabilityTypes: List<String> = emptyList()

    @EventSourcingHandler
    fun evolve(event: RuntimeCapabilitiesDetectedEvent): RuntimeCapabilityState = apply {
        currentState = RuntimeCapabilityStateEnum.DETECTED
        runtimeId = event.runtimeId
        capabilityTypes = event.capabilityTypes
    }
}

package tech.medo.runtimegovernance.runtimeidentity

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimegovernance.domain.states.RuntimeIdentityStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeIdentityTags.RUNTIME_ID)
class RuntimeIdentityState @EntityCreator constructor() {

    var currentState: RuntimeIdentityStateEnum? = null
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var revocationReason: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeIdentityActivatedEvent): RuntimeIdentityState = apply {
        currentState = RuntimeIdentityStateEnum.ACTIVE
        runtimeId = event.runtimeId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeName = event.runtimeName
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeIdentityRevokedEvent): RuntimeIdentityState = apply {
        currentState = RuntimeIdentityStateEnum.REVOKED
        runtimeId = event.runtimeId
        revocationReason = event.revocationReason
    }
}

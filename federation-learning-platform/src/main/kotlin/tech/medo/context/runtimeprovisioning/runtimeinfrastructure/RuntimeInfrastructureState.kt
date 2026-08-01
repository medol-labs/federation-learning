package tech.medo.runtimeprovisioning.runtimeinfrastructure

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetryFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeInfrastructureTags.RUNTIME_INFRASTRUCTURE_ID)
class RuntimeInfrastructureState @EntityCreator constructor() {

    var currentState: RuntimeInfrastructureStateEnum? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var agentInstallMode: String? = null
    var observedNodeCount: Int? = null
    var failureReason: String? = null
    var agentVersion: String? = null
    var organizationId: UUID? = null
    var runtimeName: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureRegisteredEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.REGISTERED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureVerifiedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.VERIFIED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        agentInstallMode = event.agentInstallMode
        observedNodeCount = event.observedNodeCount
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureVerificationFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        observedNodeCount = event.observedNodeCount
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentInstallationSucceededEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.AGENT_READY
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        agentVersion = event.agentVersion
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentInstallationFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentDeploymentRetrySucceededEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.AGENT_READY
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        agentVersion = event.agentVersion
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentDeploymentRetryFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeConnectionEstablishedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.CONNECTED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        agentInstallMode = event.agentInstallMode
        organizationId = event.organizationId
        runtimeName = event.runtimeName
    }
}

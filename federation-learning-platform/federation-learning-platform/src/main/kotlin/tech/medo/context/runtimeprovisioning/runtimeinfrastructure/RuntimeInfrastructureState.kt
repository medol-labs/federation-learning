package tech.medo.runtimeprovisioning.runtimeinfrastructure

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePlannedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
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
    var runtimeInstallationPlanId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var runtimeEnvironmentType: String? = null
    var runtimeName: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    var runtimeAgentId: UUID? = null
    var preparedNodeCount: Int? = null
    var preparationNotes: String? = null
    var observedNodeCount: Int? = null
    var failureReason: String? = null
    var agentVersion: String? = null
    var runtimeAgentEndpoint: String? = null
    var endpointScope: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructurePlannedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.PLANNED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureRegisteredEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.REGISTERED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        runtimeAgentId = event.runtimeAgentId
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructurePreparedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.PREPARED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        runtimeAgentId = event.runtimeAgentId
        preparedNodeCount = event.preparedNodeCount
        preparationNotes = event.preparationNotes
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureVerifiedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.VERIFIED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        runtimeAgentId = event.runtimeAgentId
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        observedNodeCount = event.observedNodeCount
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructureVerificationFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        runtimeAgentId = event.runtimeAgentId
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        observedNodeCount = event.observedNodeCount
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentInstallationSucceededEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.AGENT_READY
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        runtimeAgentId = event.runtimeAgentId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        agentVersion = event.agentVersion
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentInstallationFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        runtimeAgentId = event.runtimeAgentId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentDeploymentRetrySucceededEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.AGENT_READY
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        runtimeAgentId = event.runtimeAgentId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        agentVersion = event.agentVersion
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentDeploymentRetryFailedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        runtimeAgentId = event.runtimeAgentId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
        runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeConnectionEstablishedEvent): RuntimeInfrastructureState = apply {
        currentState = RuntimeInfrastructureStateEnum.CONNECTED
        runtimeInfrastructureId = event.runtimeInfrastructureId
        runtimeAgentId = event.runtimeAgentId
        agentInstallMode = event.agentInstallMode
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeName = event.runtimeName
        runtimeAgentEndpoint = event.runtimeAgentEndpoint
        endpointScope = event.endpointScope
    }
}

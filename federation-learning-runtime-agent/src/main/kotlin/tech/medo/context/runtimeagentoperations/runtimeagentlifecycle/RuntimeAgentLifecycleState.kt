package tech.medo.runtimeagentoperations.runtimeagentlifecycle

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeAgentLifecycleTags.BOOTSTRAP_REQUEST_ID)
class RuntimeAgentLifecycleState @EntityCreator constructor() {

    var currentState: RuntimeAgentLifecycleStateEnum? = null
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var agentVersion: String? = null
    var bootstrapConfigurationLoaded: Boolean? = null
    var bootstrapRequestId: UUID? = null
    var failureReason: String? = null
    var runtimeAgentSelfCheckPassed: Boolean? = null
    var configurationLoaded: Boolean? = null
    var secretStoreAccessible: Boolean? = null
    var runtimeEngineAdapterReady: Boolean? = null
    var modelRepositoryClientReady: Boolean? = null
    var localDatasetBindingStoreReady: Boolean? = null
    var workingDirectoryWritable: Boolean? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentBootstrapConfigurationLoadedEvent): RuntimeAgentLifecycleState = apply {
        currentState = RuntimeAgentLifecycleStateEnum.BOOTSTRAP_LOADED
        runtimeAgentId = event.runtimeAgentId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        agentVersion = event.agentVersion
        bootstrapConfigurationLoaded = event.bootstrapConfigurationLoaded
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentBootstrapConfigurationLoadFailedEvent): RuntimeAgentLifecycleState = apply {
        currentState = RuntimeAgentLifecycleStateEnum.BOOTSTRAP_LOADED
        bootstrapRequestId = event.bootstrapRequestId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeAgentStartedEvent): RuntimeAgentLifecycleState = apply {
        currentState = RuntimeAgentLifecycleStateEnum.STARTED
        runtimeAgentId = event.runtimeAgentId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        agentVersion = event.agentVersion
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInstanceSelfCheckPassedEvent): RuntimeAgentLifecycleState = apply {
        currentState = RuntimeAgentLifecycleStateEnum.READY
        runtimeAgentId = event.runtimeAgentId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        agentVersion = event.agentVersion
        runtimeAgentSelfCheckPassed = event.runtimeAgentSelfCheckPassed
        configurationLoaded = event.configurationLoaded
        secretStoreAccessible = event.secretStoreAccessible
        runtimeEngineAdapterReady = event.runtimeEngineAdapterReady
        modelRepositoryClientReady = event.modelRepositoryClientReady
        localDatasetBindingStoreReady = event.localDatasetBindingStoreReady
        workingDirectoryWritable = event.workingDirectoryWritable
    }
}

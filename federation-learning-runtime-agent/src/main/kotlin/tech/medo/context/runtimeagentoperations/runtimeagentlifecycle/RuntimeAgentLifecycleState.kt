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
    private var runtimeAgentId: UUID? = null
    private var runtimeInfrastructureId: UUID? = null
    private var agentVersion: String? = null
    private var bootstrapConfigurationLoaded: Boolean? = null
    private var bootstrapRequestId: UUID? = null
    private var failureReason: String? = null
    private var runtimeAgentSelfCheckPassed: Boolean? = null
    private var configurationLoaded: Boolean? = null
    private var secretStoreAccessible: Boolean? = null
    private var runtimeEngineAdapterReady: Boolean? = null
    private var modelRepositoryClientReady: Boolean? = null
    private var localDatasetBindingStoreReady: Boolean? = null
    private var workingDirectoryWritable: Boolean? = null

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

package tech.medo.runtimemonitoring.runtimenodeinventoryview

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent



interface RuntimeNodeInventoryViewReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeNodeInventoryViewReadModelProjectionUpdater(
    private val repository: RuntimeNodeInventoryViewReadModelRepository
) : RuntimeNodeInventoryViewReadModelProjectionUpdater {
    open override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    open override fun update(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeInstallationPlanCreatedEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    open override fun update(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeInfrastructureRegisteredEvent does not provide enough key fields to locate RuntimeNodeInventoryViewReadModelProjection.
    }

    @Transactional
    open override fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeNodeInventoryViewReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeNodeInventoryReportId = event.runtimeNodeInventoryReportId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeNodeName = event.runtimeNodeName
            entity.infrastructureNodeId = event.infrastructureNodeId
            entity.runtimeNodeRole = event.runtimeNodeRole
            entity.nodeReady = event.nodeReady
            entity.runtimeEngineVersion = event.runtimeEngineVersion
            entity.containerEngineVersion = event.containerEngineVersion
            entity.operatingSystem = event.operatingSystem
            entity.architecture = event.architecture
            entity.inventoryHash = event.inventoryHash
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class RuntimeNodeInventoryViewReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeNodeInventoryViewReadModelProjectionUpdater::class)
    fun defaultRuntimeNodeInventoryViewReadModelProjectionUpdater(
        repository: RuntimeNodeInventoryViewReadModelRepository
    ): RuntimeNodeInventoryViewReadModelProjectionUpdater =
        DefaultRuntimeNodeInventoryViewReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-node-inventory-view")
@Component
class RuntimeNodeInventoryViewReadModelProjector(
    private val updater: RuntimeNodeInventoryViewReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

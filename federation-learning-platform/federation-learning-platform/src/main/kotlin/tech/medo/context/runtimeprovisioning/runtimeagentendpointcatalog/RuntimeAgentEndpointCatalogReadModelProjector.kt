package tech.medo.runtimeprovisioning.runtimeagentendpointcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeAgentEndpointCatalogReadModelProjectionUpdater {
    fun update(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentRecoveredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeAgentEndpointCatalogReadModelProjectionUpdater(
    private val repository: RuntimeAgentEndpointCatalogReadModelRepository
) : RuntimeAgentEndpointCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentEndpointCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeName = event.runtimeName
            entity.runtimeAgentEndpoint = event.runtimeAgentEndpoint
            entity.endpointScope = event.endpointScope
            entity.connectionStatus = "Connected"
            entity.connectedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentEndpointCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeAgentRecoveredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentEndpointCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeAgentId) ?: RuntimeAgentEndpointCatalogReadModelProjection().apply {
                this.runtimeAgentId = event.runtimeAgentId
        }
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeId = event.runtimeId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeName = event.runtimeName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    open override fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate RuntimeAgentEndpointCatalogReadModelProjection.
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class RuntimeAgentEndpointCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeAgentEndpointCatalogReadModelProjectionUpdater::class)
    fun defaultRuntimeAgentEndpointCatalogReadModelProjectionUpdater(
        repository: RuntimeAgentEndpointCatalogReadModelRepository
    ): RuntimeAgentEndpointCatalogReadModelProjectionUpdater =
        DefaultRuntimeAgentEndpointCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-agent-endpoint-catalog")
@Component
class RuntimeAgentEndpointCatalogReadModelProjector(
    private val updater: RuntimeAgentEndpointCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentOfflineDetectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentRecoveredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

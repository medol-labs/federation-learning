package tech.medo.runtimeprovisioning.runtimeagentendpointcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-runtime-agent-endpoint-catalog")
@Component
class RuntimeAgentEndpointCatalogReadModelProjector(private val repository: RuntimeAgentEndpointCatalogReadModelRepository) {
    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(event: RuntimeIdentityRevokedEvent) {
        // Skipped: RuntimeIdentityRevokedEvent does not provide enough key fields to locate RuntimeAgentEndpointCatalogReadModelProjection.
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

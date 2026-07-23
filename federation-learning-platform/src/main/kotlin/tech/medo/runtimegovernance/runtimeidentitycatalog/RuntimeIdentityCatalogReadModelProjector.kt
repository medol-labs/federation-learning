package tech.medo.runtimegovernance.runtimeidentitycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent


@Component
class RuntimeIdentityCatalogReadModelProjector(private val repository: RuntimeIdentityCatalogReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeIdentityCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
                this.runtimeId = event.runtimeId
        }
            entity.runtimeId = event.runtimeId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.organizationId = event.organizationId
            entity.runtimeName = event.runtimeName
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
                this.runtimeId = event.runtimeId
        }
            entity.runtimeId = event.runtimeId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}

package tech.medo.runtimegovernance.runtimecapabilitycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent


@Component
class RuntimeCapabilityCatalogReadModelProjector(private val repository: RuntimeCapabilityCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeCapabilityCatalogReadModelProjection().apply {
                this.runtimeId = event.runtimeId
        }
            entity.runtimeId = event.runtimeId
            entity.capabilityTypes = event.capabilityTypes
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}

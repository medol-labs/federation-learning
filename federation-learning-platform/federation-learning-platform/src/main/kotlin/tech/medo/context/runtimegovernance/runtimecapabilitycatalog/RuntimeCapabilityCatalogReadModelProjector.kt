package tech.medo.runtimegovernance.runtimecapabilitycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-runtime-capability-catalog")
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
            entity.detectedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

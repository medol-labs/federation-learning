package tech.medo.runtimegovernance.runtimecapabilitycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimegovernance.events.RuntimeCapabilitiesDetectedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeCapabilityCatalogReadModelProjectionUpdater {
    fun update(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeCapabilityCatalogReadModelProjectionUpdater(
    private val repository: RuntimeCapabilityCatalogReadModelRepository
) : RuntimeCapabilityCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class RuntimeCapabilityCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeCapabilityCatalogReadModelProjectionUpdater::class)
    fun defaultRuntimeCapabilityCatalogReadModelProjectionUpdater(
        repository: RuntimeCapabilityCatalogReadModelRepository
    ): RuntimeCapabilityCatalogReadModelProjectionUpdater =
        DefaultRuntimeCapabilityCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-capability-catalog")
@Component
class RuntimeCapabilityCatalogReadModelProjector(
    private val updater: RuntimeCapabilityCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeCapabilitiesDetectedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

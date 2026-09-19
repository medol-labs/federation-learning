package tech.medo.trainingorchestration.runtimeengineprofilecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.trainingorchestration.events.RuntimeEngineProfileRegisteredEvent
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeEngineProfileCatalogReadModelProjectionUpdater {
    fun update(
        event: RuntimeEngineProfileRegisteredEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeEngineProfileCatalogReadModelProjectionUpdater(
    private val repository: RuntimeEngineProfileCatalogReadModelRepository
) : RuntimeEngineProfileCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: RuntimeEngineProfileRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeEngineProfileId) ?: RuntimeEngineProfileCatalogReadModelProjection().apply {
                this.runtimeEngineProfileId = event.runtimeEngineProfileId
        }
            entity.runtimeEngineProfileId = event.runtimeEngineProfileId
            entity.profileName = event.profileName
            entity.pluginProfile = event.pluginProfile
            entity.runtimeEngineImage = event.runtimeEngineImage
            entity.imageDigest = event.imageDigest
            entity.supportedModelPluginsDescription = event.supportedModelPluginsDescription
            entity.supportedAggregationAlgorithmsDescription = event.supportedAggregationAlgorithmsDescription
            entity.active = event.active
            entity.state = RuntimeEngineProfileStateEnum.REGISTERED
            entity.registeredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class RuntimeEngineProfileCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeEngineProfileCatalogReadModelProjectionUpdater::class)
    fun defaultRuntimeEngineProfileCatalogReadModelProjectionUpdater(
        repository: RuntimeEngineProfileCatalogReadModelRepository
    ): RuntimeEngineProfileCatalogReadModelProjectionUpdater =
        DefaultRuntimeEngineProfileCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-engine-profile-catalog")
@Component
class RuntimeEngineProfileCatalogReadModelProjector(
    private val updater: RuntimeEngineProfileCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeEngineProfileRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

package tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.shared.application.outbox.MedolOutboxAppender

import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationSetEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueTranslationUpdatedEvent



interface DictionaryValueTranslationCatalogReadModelProjectionUpdater {
    fun update(
        event: DictionaryValueTranslationSetEvent,
        message: EventMessage
    )

    fun update(
        event: DictionaryValueTranslationUpdatedEvent,
        message: EventMessage
    )
}

open class DefaultDictionaryValueTranslationCatalogReadModelProjectionUpdater(
    private val repository: DictionaryValueTranslationCatalogReadModelRepository,
    private val outbox: MedolOutboxAppender
) : DictionaryValueTranslationCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: DictionaryValueTranslationSetEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryValueTranslationId) ?: DictionaryValueTranslationCatalogReadModelProjection().apply {
                this.dictionaryValueTranslationId = event.dictionaryValueTranslationId
        }
            entity.dictionaryValueTranslationId = event.dictionaryValueTranslationId
            entity.dictionaryValueId = event.dictionaryValueId
            entity.dictionaryCode = event.dictionaryCode.value
            entity.valueCode = event.valueCode.value
            entity.locale = event.locale.value
            entity.displayName = event.displayName
            entity.description = event.description
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

        outbox.appendReadModel(
            sourceContext = "DictionaryMaintenance",
            sourceReadModel = "DictionaryValueTranslationCatalog",
            readModelKey = event.dictionaryValueTranslationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    open override fun update(
        event: DictionaryValueTranslationUpdatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryValueTranslationId) ?: DictionaryValueTranslationCatalogReadModelProjection().apply {
                this.dictionaryValueTranslationId = event.dictionaryValueTranslationId
        }
            entity.dictionaryValueTranslationId = event.dictionaryValueTranslationId
            entity.dictionaryValueId = event.dictionaryValueId
            entity.dictionaryCode = event.dictionaryCode.value
            entity.valueCode = event.valueCode.value
            entity.locale = event.locale.value
            entity.displayName = event.displayName
            entity.description = event.description
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

        outbox.appendReadModel(
            sourceContext = "DictionaryMaintenance",
            sourceReadModel = "DictionaryValueTranslationCatalog",
            readModelKey = event.dictionaryValueTranslationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

}

@Configuration(proxyBeanMethods = false)
class DictionaryValueTranslationCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(DictionaryValueTranslationCatalogReadModelProjectionUpdater::class)
    fun defaultDictionaryValueTranslationCatalogReadModelProjectionUpdater(
        repository: DictionaryValueTranslationCatalogReadModelRepository,
        outbox: MedolOutboxAppender
    ): DictionaryValueTranslationCatalogReadModelProjectionUpdater =
        DefaultDictionaryValueTranslationCatalogReadModelProjectionUpdater(repository, outbox)
}

@Namespace("readmodel-dictionary-value-translation-catalog")
@Component
class DictionaryValueTranslationCatalogReadModelProjector(
    private val updater: DictionaryValueTranslationCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: DictionaryValueTranslationSetEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DictionaryValueTranslationUpdatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

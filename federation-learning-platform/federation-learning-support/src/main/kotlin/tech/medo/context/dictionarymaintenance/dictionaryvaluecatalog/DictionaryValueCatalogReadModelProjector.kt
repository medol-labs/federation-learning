package tech.medo.dictionarymaintenance.dictionaryvaluecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.dictionarymaintenance.events.DictionaryValueAddedEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueDisabledEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueEnabledEvent
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface DictionaryValueCatalogReadModelProjectionUpdater {
    fun update(
        event: DictionaryValueAddedEvent,
        message: EventMessage
    )

    fun update(
        event: DictionaryValueDisabledEvent,
        message: EventMessage
    )

    fun update(
        event: DictionaryValueEnabledEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(DictionaryValueCatalogReadModelProjectionUpdater::class)
class DefaultDictionaryValueCatalogReadModelProjectionUpdater(
    private val repository: DictionaryValueCatalogReadModelRepository
) : DictionaryValueCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: DictionaryValueAddedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
                this.dictionaryValueId = event.dictionaryValueId
        }
            entity.dictionaryValueId = event.dictionaryValueId
            entity.dictionaryId = event.dictionaryId
            entity.dictionaryCode = event.dictionaryCode.value
            entity.valueCode = event.valueCode.value
            entity.displayName = event.displayName
            entity.displayOrder = event.displayOrder?.value
            entity.description = event.description
            entity.active = event.active
            entity.state = DictionaryValueStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DictionaryValueDisabledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
                this.dictionaryValueId = event.dictionaryValueId
        }
            entity.dictionaryValueId = event.dictionaryValueId
            entity.disabledReason = event.disabledReason
            entity.state = DictionaryValueStateEnum.DISABLED
            entity.active = false
            entity.disabledAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DictionaryValueEnabledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
                this.dictionaryValueId = event.dictionaryValueId
        }
            entity.dictionaryValueId = event.dictionaryValueId
            entity.state = DictionaryValueStateEnum.ACTIVE
            entity.active = true
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Namespace("readmodel-dictionary-value-catalog")
@Component
class DictionaryValueCatalogReadModelProjector(
    private val updater: DictionaryValueCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: DictionaryValueAddedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DictionaryValueDisabledEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DictionaryValueEnabledEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

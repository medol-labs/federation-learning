package tech.medo.infrastructure.secondary.dictionarymaintenance.dictionaryvaluecatalog

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelProjectionUpdater
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelRepository
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.toReadModel
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum
import tech.medo.dictionarymaintenance.events.DictionaryValueAddedEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueDisabledEvent
import tech.medo.dictionarymaintenance.events.DictionaryValueEnabledEvent
import tech.medo.shared.application.metadata.ProjectionMetadata
import tech.medo.shared.application.sync.SyncOutboxAppender

@Component
class DictionaryValueCatalogReadModelOutboxProjectionUpdater(
    private val repository: DictionaryValueCatalogReadModelRepository,
    private val outbox: SyncOutboxAppender
) : DictionaryValueCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: DictionaryValueAddedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
            dictionaryValueId = event.dictionaryValueId
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
        saveAndPublish(entity, event.dictionaryValueId.toString(), message)
    }

    @Transactional
    override fun update(
        event: DictionaryValueDisabledEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
            dictionaryValueId = event.dictionaryValueId
        }
        entity.dictionaryValueId = event.dictionaryValueId
        entity.disabledReason = event.disabledReason
        entity.state = DictionaryValueStateEnum.DISABLED
        entity.active = false
        entity.disabledAt = eventTime(message)
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.dictionaryValueId.toString(), message)
    }

    @Transactional
    override fun update(
        event: DictionaryValueEnabledEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.dictionaryValueId) ?: DictionaryValueCatalogReadModelProjection().apply {
            dictionaryValueId = event.dictionaryValueId
        }
        entity.dictionaryValueId = event.dictionaryValueId
        entity.state = DictionaryValueStateEnum.ACTIVE
        entity.active = true
        entity.enabledAt = eventTime(message)
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.dictionaryValueId.toString(), message)
    }

    private fun saveAndPublish(
        entity: DictionaryValueCatalogReadModelProjection,
        readModelKey: String,
        message: EventMessage
    ) {
        repository.save(entity)
        outbox.appendReadModel(
            sourceContext = "DictionaryMaintenance",
            sourceReadModel = "DictionaryValueCatalog",
            readModelKey = readModelKey,
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)
}

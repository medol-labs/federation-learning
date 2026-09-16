package tech.medo.dictionarymaintenance.dictionarycatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.dictionarymaintenance.events.DictionaryRegisteredEvent
import tech.medo.dictionarymaintenance.events.DictionaryUpdatedEvent
import tech.medo.dictionarymaintenance.events.DictionaryArchivedEvent
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface DictionaryCatalogReadModelProjectionUpdater {
    fun update(
        event: DictionaryRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: DictionaryUpdatedEvent,
        message: EventMessage
    )

    fun update(
        event: DictionaryArchivedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(DictionaryCatalogReadModelProjectionUpdater::class)
class DefaultDictionaryCatalogReadModelProjectionUpdater(
    private val repository: DictionaryCatalogReadModelRepository
) : DictionaryCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: DictionaryRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryId) ?: DictionaryCatalogReadModelProjection().apply {
                this.dictionaryId = event.dictionaryId
        }
            entity.dictionaryId = event.dictionaryId
            entity.dictionaryCode = event.dictionaryCode.value
            entity.dictionaryName = event.dictionaryName
            entity.description = event.description
            entity.state = DictionaryStateEnum.REGISTERED
            entity.registeredAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DictionaryUpdatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryId) ?: DictionaryCatalogReadModelProjection().apply {
                this.dictionaryId = event.dictionaryId
        }
            entity.dictionaryId = event.dictionaryId
            entity.dictionaryName = event.dictionaryName
            entity.description = event.description
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DictionaryArchivedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.dictionaryId) ?: DictionaryCatalogReadModelProjection().apply {
                this.dictionaryId = event.dictionaryId
        }
            entity.dictionaryId = event.dictionaryId
            entity.archiveReason = event.archiveReason
            entity.state = DictionaryStateEnum.ARCHIVED
            entity.archivedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Namespace("readmodel-dictionary-catalog")
@Component
class DictionaryCatalogReadModelProjector(
    private val updater: DictionaryCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: DictionaryRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DictionaryUpdatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DictionaryArchivedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

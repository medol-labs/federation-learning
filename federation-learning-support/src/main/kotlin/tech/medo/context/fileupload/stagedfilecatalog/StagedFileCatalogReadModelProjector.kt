package tech.medo.fileupload.stagedfilecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.events.StagedFileConsumedEvent
import tech.medo.fileupload.events.StagedFileDiscardedEvent
import tech.medo.fileupload.events.StagedFileExpiredEvent
import tech.medo.fileupload.domain.states.StagedFileStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class StagedFileCatalogReadModelProjector(private val repository: StagedFileCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: FileUploadStagedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.stagedFileId) ?: StagedFileCatalogReadModelProjection().apply {
                this.stagedFileId = event.stagedFileId
        }
            entity.stagedFileId = event.stagedFileId
            entity.originalFileName = event.originalFileName
            entity.contentType = event.contentType
            entity.sizeBytes = event.sizeBytes
            entity.purpose = event.purpose
            entity.stagedFileLocation = event.stagedFileLocation
            entity.checksum = event.checksum
            entity.expiresAt = event.expiresAt
            entity.state = StagedFileStateEnum.STAGED
            entity.stagedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: StagedFileConsumedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.stagedFileId) ?: StagedFileCatalogReadModelProjection().apply {
                this.stagedFileId = event.stagedFileId
        }
            entity.stagedFileId = event.stagedFileId
            entity.consumedByContext = event.consumedByContext
            entity.consumedByCommand = event.consumedByCommand
            entity.consumedByCommandId = event.consumedByCommandId
            entity.state = StagedFileStateEnum.CONSUMED
            entity.consumedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: StagedFileDiscardedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.stagedFileId) ?: StagedFileCatalogReadModelProjection().apply {
                this.stagedFileId = event.stagedFileId
        }
            entity.stagedFileId = event.stagedFileId
            entity.discardReason = event.discardReason
            entity.state = StagedFileStateEnum.DISCARDED
            entity.discardedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: StagedFileExpiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.stagedFileId) ?: StagedFileCatalogReadModelProjection().apply {
                this.stagedFileId = event.stagedFileId
        }
            entity.stagedFileId = event.stagedFileId
            entity.expiredAt = event.expiredAt
            entity.expirationReason = event.expirationReason
            entity.state = StagedFileStateEnum.EXPIRED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

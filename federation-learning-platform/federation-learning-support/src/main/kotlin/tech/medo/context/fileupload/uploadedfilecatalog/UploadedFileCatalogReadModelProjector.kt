package tech.medo.fileupload.uploadedfilecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.domain.states.UploadedFileStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-uploaded-file-catalog")
@Component
class UploadedFileCatalogReadModelProjector(private val repository: UploadedFileCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: FileUploadedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.fileId) ?: UploadedFileCatalogReadModelProjection().apply {
                this.fileId = event.fileId
        }
            entity.fileId = event.fileId
            entity.originalFileName = event.originalFileName
            entity.contentType = event.contentType
            entity.sizeBytes = event.sizeBytes
            entity.purpose = event.purpose
            entity.fileLocation = event.fileLocation
            entity.checksum = event.checksum
            entity.expiresAt = event.expiresAt
            entity.state = UploadedFileStateEnum.AVAILABLE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FileReferencedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.fileId) ?: UploadedFileCatalogReadModelProjection().apply {
                this.fileId = event.fileId
        }
            entity.fileId = event.fileId
            entity.referencedByContext = event.referencedByContext
            entity.referencedByCommand = event.referencedByCommand
            entity.referencedByCommandId = event.referencedByCommandId
            entity.state = UploadedFileStateEnum.REFERENCED
            entity.referencedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FileDiscardedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.fileId) ?: UploadedFileCatalogReadModelProjection().apply {
                this.fileId = event.fileId
        }
            entity.fileId = event.fileId
            entity.discardReason = event.discardReason
            entity.state = UploadedFileStateEnum.DISCARDED
            entity.discardedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: FileExpiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.fileId) ?: UploadedFileCatalogReadModelProjection().apply {
                this.fileId = event.fileId
        }
            entity.fileId = event.fileId
            entity.expiredAt = event.expiredAt
            entity.expirationReason = event.expirationReason
            entity.state = UploadedFileStateEnum.EXPIRED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

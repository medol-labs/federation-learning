package tech.medo.fileupload.uploadedfilecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.domain.states.UploadedFileStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface UploadedFileCatalogReadModelProjectionUpdater {
    fun update(
        event: FileUploadedEvent,
        message: EventMessage
    )

    fun update(
        event: FileReferencedEvent,
        message: EventMessage
    )

    fun update(
        event: FileDiscardedEvent,
        message: EventMessage
    )

    fun update(
        event: FileExpiredEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(UploadedFileCatalogReadModelProjectionUpdater::class)
class DefaultUploadedFileCatalogReadModelProjectionUpdater(
    private val repository: UploadedFileCatalogReadModelRepository
) : UploadedFileCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

    @Transactional
    override fun update(
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

@Namespace("readmodel-uploaded-file-catalog")
@Component
class UploadedFileCatalogReadModelProjector(
    private val updater: UploadedFileCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: FileUploadedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FileReferencedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FileDiscardedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: FileExpiredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

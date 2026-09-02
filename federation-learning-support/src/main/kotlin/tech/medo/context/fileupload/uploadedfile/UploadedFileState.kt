package tech.medo.fileupload.uploadedfile

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.fileupload.events.FileUploadedEvent
import tech.medo.fileupload.events.FileReferencedEvent
import tech.medo.fileupload.events.FileDownloadAuthorizedEvent
import tech.medo.fileupload.events.FileDiscardedEvent
import tech.medo.fileupload.events.FileExpiredEvent
import tech.medo.fileupload.domain.states.UploadedFileStateEnum

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = UploadedFileTags.FILE_ID)
class UploadedFileState @EntityCreator constructor() {

    var currentState: UploadedFileStateEnum? = null
    var fileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var fileLocation: String? = null
    var checksum: String? = null
    var expiresAt: LocalDateTime? = null
    var referencedByContext: String? = null
    var referencedByCommand: String? = null
    var referencedByCommandId: UUID? = null
    var downloadUri: String? = null
    var discardReason: String? = null
    var expiredAt: LocalDateTime? = null
    var expirationReason: String? = null

    @EventSourcingHandler
    fun evolve(event: FileUploadedEvent): UploadedFileState = apply {
        currentState = UploadedFileStateEnum.AVAILABLE
        fileId = event.fileId
        originalFileName = event.originalFileName
        contentType = event.contentType
        sizeBytes = event.sizeBytes
        purpose = event.purpose
        fileLocation = event.fileLocation
        checksum = event.checksum
        expiresAt = event.expiresAt
    }

    @EventSourcingHandler
    fun evolve(event: FileReferencedEvent): UploadedFileState = apply {
        currentState = UploadedFileStateEnum.REFERENCED
        fileId = event.fileId
        referencedByContext = event.referencedByContext
        referencedByCommand = event.referencedByCommand
        referencedByCommandId = event.referencedByCommandId
    }

    @EventSourcingHandler
    fun evolve(event: FileDownloadAuthorizedEvent): UploadedFileState = apply {
        fileId = event.fileId
        originalFileName = event.originalFileName
        contentType = event.contentType
        sizeBytes = event.sizeBytes
        downloadUri = event.downloadUri
    }

    @EventSourcingHandler
    fun evolve(event: FileDiscardedEvent): UploadedFileState = apply {
        currentState = UploadedFileStateEnum.DISCARDED
        fileId = event.fileId
        discardReason = event.discardReason
    }

    @EventSourcingHandler
    fun evolve(event: FileExpiredEvent): UploadedFileState = apply {
        currentState = UploadedFileStateEnum.EXPIRED
        fileId = event.fileId
        expiredAt = event.expiredAt
        expirationReason = event.expirationReason
    }
}

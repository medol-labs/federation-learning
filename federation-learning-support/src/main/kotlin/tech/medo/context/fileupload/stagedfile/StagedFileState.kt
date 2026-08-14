package tech.medo.fileupload.stagedfile

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.fileupload.events.FileUploadStagedEvent
import tech.medo.fileupload.events.StagedFileConsumedEvent
import tech.medo.fileupload.events.StagedFileDiscardedEvent
import tech.medo.fileupload.events.StagedFileExpiredEvent
import tech.medo.fileupload.domain.states.StagedFileStateEnum

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = StagedFileTags.STAGED_FILE_ID)
class StagedFileState @EntityCreator constructor() {

    var currentState: StagedFileStateEnum? = null
    var stagedFileId: UUID? = null
    var originalFileName: String? = null
    var contentType: String? = null
    var sizeBytes: Long? = null
    var purpose: String? = null
    var stagedFileLocation: String? = null
    var checksum: String? = null
    var expiresAt: LocalDateTime? = null
    var consumedByContext: String? = null
    var consumedByCommand: String? = null
    var consumedByCommandId: UUID? = null
    var discardReason: String? = null
    var expiredAt: LocalDateTime? = null
    var expirationReason: String? = null

    @EventSourcingHandler
    fun evolve(event: FileUploadStagedEvent): StagedFileState = apply {
        currentState = StagedFileStateEnum.STAGED
        stagedFileId = event.stagedFileId
        originalFileName = event.originalFileName
        contentType = event.contentType
        sizeBytes = event.sizeBytes
        purpose = event.purpose
        stagedFileLocation = event.stagedFileLocation
        checksum = event.checksum
        expiresAt = event.expiresAt
    }

    @EventSourcingHandler
    fun evolve(event: StagedFileConsumedEvent): StagedFileState = apply {
        currentState = StagedFileStateEnum.CONSUMED
        stagedFileId = event.stagedFileId
        consumedByContext = event.consumedByContext
        consumedByCommand = event.consumedByCommand
        consumedByCommandId = event.consumedByCommandId
    }

    @EventSourcingHandler
    fun evolve(event: StagedFileDiscardedEvent): StagedFileState = apply {
        currentState = StagedFileStateEnum.DISCARDED
        stagedFileId = event.stagedFileId
        discardReason = event.discardReason
    }

    @EventSourcingHandler
    fun evolve(event: StagedFileExpiredEvent): StagedFileState = apply {
        currentState = StagedFileStateEnum.EXPIRED
        stagedFileId = event.stagedFileId
        expiredAt = event.expiredAt
        expirationReason = event.expirationReason
    }
}

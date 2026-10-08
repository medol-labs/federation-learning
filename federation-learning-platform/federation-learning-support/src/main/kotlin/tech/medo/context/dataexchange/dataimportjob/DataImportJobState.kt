package tech.medo.dataexchange.dataimportjob

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dataexchange.events.DataImportRequestedEvent
import tech.medo.dataexchange.events.DataImportProcessingStartedEvent
import tech.medo.dataexchange.events.DataImportCompletedEvent
import tech.medo.dataexchange.events.DataImportFailedEvent

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@EventSourced(idType = UUID::class, tagKey = DataImportJobTags.DATA_IMPORT_JOB_ID)
class DataImportJobState @EntityCreator constructor() {

    var dataImportJobId: UUID? = null
    var commandName: String? = null
    var resourceName: String? = null
    var originalFileName: String? = null
    var filePath: String? = null
    var requestedLocale: String? = null
    var requestedAt: LocalDateTime? = null
    var requestHash: String? = null
    var status: String? = null
    var totalRows: Long? = null
    var successRows: Long? = null
    var failedRows: Long? = null
    var failureIndexPath: String? = null
    var errorMessage: String? = null

    @EventSourcingHandler
    fun evolve(event: DataImportRequestedEvent): DataImportJobState = apply {
        dataImportJobId = event.dataImportJobId
        commandName = event.commandName
        resourceName = event.resourceName
        originalFileName = event.originalFileName
        filePath = event.filePath
        requestedLocale = event.requestedLocale
        requestedAt = event.requestedAt
        requestHash = event.requestHash
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataImportProcessingStartedEvent): DataImportJobState = apply {
        dataImportJobId = event.dataImportJobId
        totalRows = event.totalRows
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataImportCompletedEvent): DataImportJobState = apply {
        dataImportJobId = event.dataImportJobId
        totalRows = event.totalRows
        successRows = event.successRows
        failedRows = event.failedRows
        failureIndexPath = event.failureIndexPath
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataImportFailedEvent): DataImportJobState = apply {
        dataImportJobId = event.dataImportJobId
        errorMessage = event.errorMessage
        status = event.status
    }
}

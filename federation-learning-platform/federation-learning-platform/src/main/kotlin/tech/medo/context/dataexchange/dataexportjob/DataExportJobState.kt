package tech.medo.dataexchange.dataexportjob

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.dataexchange.events.DataExportRequestedEvent
import tech.medo.dataexchange.events.DataExportProcessingStartedEvent
import tech.medo.dataexchange.events.DataExportCompletedEvent
import tech.medo.dataexchange.events.DataExportFailedEvent

import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat


@EventSourced(idType = UUID::class, tagKey = DataExportJobTags.DATA_EXPORT_JOB_ID)
class DataExportJobState @EntityCreator constructor() {

    var dataExportJobId: UUID? = null
    var resourceName: String? = null
    var criteriaJson: String? = null
    var sortJson: String? = null
    var columnsJson: String? = null
    var requestedLocale: String? = null
    var requestedAt: LocalDateTime? = null
    var snapshotUpperBound: LocalDateTime? = null
    var requestHash: String? = null
    var fileName: String? = null
    var status: String? = null
    var filePath: String? = null
    var rowCount: Long? = null
    var errorMessage: String? = null

    @EventSourcingHandler
    fun evolve(event: DataExportRequestedEvent): DataExportJobState = apply {
        dataExportJobId = event.dataExportJobId
        resourceName = event.resourceName
        criteriaJson = event.criteriaJson
        sortJson = event.sortJson
        columnsJson = event.columnsJson
        requestedLocale = event.requestedLocale
        requestedAt = event.requestedAt
        snapshotUpperBound = event.snapshotUpperBound
        requestHash = event.requestHash
        fileName = event.fileName
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataExportProcessingStartedEvent): DataExportJobState = apply {
        dataExportJobId = event.dataExportJobId
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataExportCompletedEvent): DataExportJobState = apply {
        dataExportJobId = event.dataExportJobId
        fileName = event.fileName
        filePath = event.filePath
        rowCount = event.rowCount
        status = event.status
    }

    @EventSourcingHandler
    fun evolve(event: DataExportFailedEvent): DataExportJobState = apply {
        dataExportJobId = event.dataExportJobId
        errorMessage = event.errorMessage
        status = event.status
    }
}

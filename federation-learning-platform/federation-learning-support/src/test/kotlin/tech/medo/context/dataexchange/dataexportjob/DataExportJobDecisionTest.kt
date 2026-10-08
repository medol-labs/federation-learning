package tech.medo.dataexchange.dataexportjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.dataexchange.dataexportjob.RequestDataExportCommand
import tech.medo.dataexchange.events.DataExportRequestedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class DataExportJobDecisionTest {
    @Test
    fun RequestDataExportEmitsDataExportRequestedEvent() {
        val events = (object : DataExportJobDecision {}).decide(
            RequestDataExportCommand(
            dataExportJobId = java.util.UUID.randomUUID(),
            resourceName = "",
            criteriaJson = "",
            sortJson = "",
            columnsJson = "",
            requestedLocale = null,
            requestedAt = java.time.LocalDateTime.now(),
            snapshotUpperBound = java.time.LocalDateTime.now(),
            requestHash = "",
            fileName = "",
            status = ""
            )
        )

        assertTrue(events.any { it is DataExportRequestedEvent })
    }
}

package tech.medo.dataexchange.dataimportjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.dataexchange.dataimportjob.RequestDataImportCommand
import tech.medo.dataexchange.events.DataImportRequestedEvent
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class DataImportJobDecisionTest {
    @Test
    fun RequestDataImportEmitsDataImportRequestedEvent() {
        val events = (object : DataImportJobDecision {}).decide(
            RequestDataImportCommand(
            dataImportJobId = java.util.UUID.randomUUID(),
            commandName = "",
            resourceName = "",
            originalFileName = "",
            filePath = "",
            requestedLocale = null,
            requestedAt = java.time.LocalDateTime.now(),
            requestHash = "",
            status = ""
            )
        )

        assertTrue(events.any { it is DataImportRequestedEvent })
    }
}

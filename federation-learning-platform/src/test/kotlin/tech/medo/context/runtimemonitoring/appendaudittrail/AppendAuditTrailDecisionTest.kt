package tech.medo.runtimemonitoring.appendaudittrail

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand
import tech.medo.runtimemonitoring.events.AuditTrailAppendedEvent
import java.util.UUID

class AppendAuditTrailDecisionTest {
    @Test
    fun AppendAuditTrailEmitsAuditTrailAppendedEvent() {
        val events = (object : AppendAuditTrailDecision {}).decide(
            AppendAuditTrailCommand(
            auditRecordId = java.util.UUID.randomUUID(),
            sourceEventName = "",
            sourceEntityId = null,
            severity = "",
            payloadHash = ""
            )
        )

        assertTrue(events.any { it is AuditTrailAppendedEvent })
    }
}

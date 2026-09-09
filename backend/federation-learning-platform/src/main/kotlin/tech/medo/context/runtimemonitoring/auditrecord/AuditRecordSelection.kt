package tech.medo.runtimemonitoring.auditrecord

import java.util.UUID;


data class AuditRecordSelection(
    val auditRecordId: UUID
)

object AuditRecordTags {
    const val AUDIT_RECORD_ID = "auditRecordId"
}

object AuditRecordMetadata {
    val concepts = listOf("AuditRecord")
}

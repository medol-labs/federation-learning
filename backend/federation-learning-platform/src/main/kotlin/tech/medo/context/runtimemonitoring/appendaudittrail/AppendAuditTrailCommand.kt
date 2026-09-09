package tech.medo.runtimemonitoring.appendaudittrail

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.auditrecord.AuditRecordSelection
import java.util.UUID;


@Command
data class AppendAuditTrailCommand(
    val auditRecordId: UUID = java.util.UUID.randomUUID(),
    val sourceEventName: String,
    val sourceEntityId: UUID?,
    val severity: String,
    val payloadHash: String
) {
    @TargetEntityId
    val selection: AuditRecordSelection = AuditRecordSelection(auditRecordId = auditRecordId)

}

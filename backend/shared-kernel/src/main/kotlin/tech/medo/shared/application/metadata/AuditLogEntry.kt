package tech.medo.shared.application.metadata

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "audit_log")
class AuditLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    @Column(name = "event_type")
    var eventType: String? = null

    @Column(name = "event_timestamp")
    var timestamp: Instant? = null

    @Lob
    @Column(columnDefinition = "TEXT")
    var payload: String? = null
}

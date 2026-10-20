package tech.medo.shared.application.metadata

import org.axonframework.messaging.eventhandling.EventMessage
import java.time.LocalDateTime
import java.time.ZoneOffset

interface MetadataProjection {
    fun assignMetadata(metadata: MetadataSnapshot, updateTimestamp: Boolean = true)
}

data class MetadataSnapshot(
    val projectionUpdatedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)

object ProjectionMetadata {
    fun from(message: EventMessage): MetadataSnapshot {
        val metadata = message.metadata()

        return MetadataSnapshot(
            projectionUpdatedAt = LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC),
            userId = MetadataFactory.value(metadata[MetadataKeys.USER_ID]),
            sessionId = MetadataFactory.value(metadata[MetadataKeys.SESSION_ID]),
            correlationId = MetadataFactory.value(metadata[MetadataKeys.CORRELATION_ID]),
            causationId = MetadataFactory.value(metadata[MetadataKeys.CAUSATION_ID]),
            traceId = MetadataFactory.value(metadata[MetadataKeys.TRACE_ID]),
            tenantId = MetadataFactory.value(metadata[MetadataKeys.TENANT_ID])
        )
    }

    fun assign(target: MetadataProjection, message: EventMessage) {
        assign(target, from(message))
    }

    fun assign(target: MetadataProjection, metadata: MetadataSnapshot) {
        target.assignMetadata(metadata)
    }

    fun assign(
        target: MetadataProjection,
        userId: String?,
        sessionId: String?,
        correlationId: String?,
        causationId: String?,
        traceId: String?,
        tenantId: String?
    ) {
        target.assignMetadata(MetadataSnapshot(
            projectionUpdatedAt = null,
            userId = MetadataFactory.value(userId),
            sessionId = MetadataFactory.value(sessionId),
            correlationId = MetadataFactory.value(correlationId),
            causationId = MetadataFactory.value(causationId),
            traceId = MetadataFactory.value(traceId),
            tenantId = MetadataFactory.value(tenantId)
        ), updateTimestamp = false)
    }
}

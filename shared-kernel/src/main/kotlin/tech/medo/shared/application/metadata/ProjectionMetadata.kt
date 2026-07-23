package tech.medo.shared.application.metadata

import org.axonframework.messaging.eventhandling.EventMessage

interface MetadataProjection {
    var userId: String?
    var sessionId: String?
    var correlationId: String?
    var causationId: String?
    var traceId: String?
    var tenantId: String?
}

data class MetadataSnapshot(
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
        target.userId = metadata.userId
        target.sessionId = metadata.sessionId
        target.correlationId = metadata.correlationId
        target.causationId = metadata.causationId
        target.traceId = metadata.traceId
        target.tenantId = metadata.tenantId
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
        target.userId = MetadataFactory.value(userId)
        target.sessionId = MetadataFactory.value(sessionId)
        target.correlationId = MetadataFactory.value(correlationId)
        target.causationId = MetadataFactory.value(causationId)
        target.traceId = MetadataFactory.value(traceId)
        target.tenantId = MetadataFactory.value(tenantId)
    }
}

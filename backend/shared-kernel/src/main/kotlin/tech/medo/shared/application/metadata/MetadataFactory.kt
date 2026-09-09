package tech.medo.shared.application.metadata

import jakarta.servlet.http.HttpServletRequest
import org.axonframework.messaging.core.Metadata
import java.util.UUID

object MetadataFactory {
    const val DEFAULT_USER_ID = "anonymous"

    fun from(request: HttpServletRequest): Metadata {
        val correlationId = header(request, MetadataHeaders.CORRELATION_ID) ?: UUID.randomUUID().toString()
        val values = linkedMapOf<String, String?>()

        put(values, MetadataKeys.USER_ID, header(request, MetadataHeaders.USER_ID) ?: request.userPrincipal?.name ?: DEFAULT_USER_ID)
        put(values, MetadataKeys.SESSION_ID, header(request, MetadataHeaders.SESSION_ID) ?: request.requestedSessionId ?: request.getSession(false)?.id ?: UUID.randomUUID().toString())
        put(values, MetadataKeys.CORRELATION_ID, correlationId)
        put(values, MetadataKeys.CAUSATION_ID, header(request, MetadataHeaders.CAUSATION_ID) ?: correlationId)
        put(values, MetadataKeys.TRACE_ID, header(request, MetadataHeaders.TRACE_ID) ?: traceIdFromTraceParent(request) ?: correlationId)
        put(values, MetadataKeys.TENANT_ID, header(request, MetadataHeaders.TENANT_ID))

        return Metadata.from(values)
    }

    fun fromValues(
        userId: String?,
        sessionId: String?,
        correlationId: String?,
        causationId: String?,
        traceId: String?,
        tenantId: String?
    ): Metadata {
        val resolvedCorrelationId = value(correlationId) ?: UUID.randomUUID().toString()
        val values = linkedMapOf<String, String?>()

        put(values, MetadataKeys.USER_ID, value(userId) ?: DEFAULT_USER_ID)
        put(values, MetadataKeys.SESSION_ID, value(sessionId) ?: UUID.randomUUID().toString())
        put(values, MetadataKeys.CORRELATION_ID, resolvedCorrelationId)
        put(values, MetadataKeys.CAUSATION_ID, value(causationId) ?: resolvedCorrelationId)
        put(values, MetadataKeys.TRACE_ID, value(traceId) ?: resolvedCorrelationId)
        put(values, MetadataKeys.TENANT_ID, value(tenantId))

        return Metadata.from(values)
    }

    fun value(value: String?): String? =
        value?.trim()?.takeIf { it.isNotEmpty() }

    private fun header(request: HttpServletRequest, name: String): String? =
        value(request.getHeader(name))

    private fun put(values: MutableMap<String, String?>, key: String, value: String?) {
        value?.let { values[key] = it }
    }

    private fun traceIdFromTraceParent(request: HttpServletRequest): String? =
        header(request, MetadataHeaders.TRACE_PARENT)
            ?.split("-")
            ?.getOrNull(1)
            ?.takeIf { it.length == 32 }
}

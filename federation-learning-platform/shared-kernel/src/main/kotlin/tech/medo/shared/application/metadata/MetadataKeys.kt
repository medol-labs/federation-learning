package tech.medo.shared.application.metadata

object MetadataKeys {
    const val USER_ID = "userId"
    const val SESSION_ID = "sessionId"
    const val CORRELATION_ID = "correlationId"
    const val CAUSATION_ID = "causationId"
    const val TRACE_ID = "traceId"
    const val TENANT_ID = "tenantId"

    val PROPAGATED_KEYS = arrayOf(
        CORRELATION_ID,
        CAUSATION_ID,
        USER_ID,
        SESSION_ID,
        TRACE_ID,
        TENANT_ID
    )
}

object MetadataHeaders {
    const val USER_ID = "X-User-Id"
    const val SESSION_ID = "X-Session-Id"
    const val CORRELATION_ID = "X-Correlation-Id"
    const val CAUSATION_ID = "X-Causation-Id"
    const val TRACE_ID = "X-Trace-Id"
    const val TRACE_PARENT = "traceparent"
    const val TENANT_ID = "X-Tenant-Id"
}

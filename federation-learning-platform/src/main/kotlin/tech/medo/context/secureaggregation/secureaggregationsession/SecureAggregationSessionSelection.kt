package tech.medo.secureaggregation.secureaggregationsession

import java.util.UUID;


data class SecureAggregationSessionSelection(
    val secureAggregationSessionId: UUID
)

object SecureAggregationSessionTags {
    const val SECURE_AGGREGATION_SESSION_ID = "secureAggregationSessionId"
}

object SecureAggregationSessionMetadata {
    val concepts = listOf("SecureAggregationSession")
}

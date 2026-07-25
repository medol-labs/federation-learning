package tech.medo.secureaggregation.domain.states

enum class SecureAggregationSessionStateEnum {
    PLANNED,
    PARTICIPANTS_SELECTED,
    ENCRYPTION_CONTEXT_PREPARED,
    COMPLETED,
    FAILED
}

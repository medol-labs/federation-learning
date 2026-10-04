package tech.medo.secureaggregation.domain.states

enum class SecureAggregationSessionStateEnum {
    Planned,
    ParticipantsSelected,
    EncryptionContextPrepared,
    Completed,
    Failed
}

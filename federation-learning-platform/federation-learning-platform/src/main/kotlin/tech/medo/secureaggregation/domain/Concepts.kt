package tech.medo.secureaggregation.domain

object Concepts {
    data object SecureAggregationSession {
        const val NAME = "SecureAggregationSession"
        val slices = listOf("CreateSecureAggregationSession", "SelectSecureAggregationParticipants", "PrepareHomomorphicEncryptionContext", "RecordEncryptedModelUpdate", "CompleteHomomorphicAggregationSession", "FailSecureAggregationSession", "SecureAggregationSessionCatalog")
        val states = listOf("Planned", "ParticipantsSelected", "EncryptionContextPrepared", "Completed", "Failed")
    }
}

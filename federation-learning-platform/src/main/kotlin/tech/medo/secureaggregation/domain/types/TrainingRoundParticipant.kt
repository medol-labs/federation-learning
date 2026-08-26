package tech.medo.secureaggregation.domain.types

import java.util.UUID;


data class TrainingRoundParticipant(
    val organizationId: UUID,
    val runtimeId: UUID,
    val datasetId: UUID
)

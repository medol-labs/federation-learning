package tech.medo.trainingorchestration.traininground

import java.util.UUID;


data class TrainingRoundSelection(
    val roundId: UUID
)

object TrainingRoundTags {
    const val ROUND_ID = "roundId"
}

object TrainingRoundMetadata {
    val concepts = listOf("TrainingRound")
}

package tech.medo.trainingorchestration.traininground

import java.util.UUID;


data class TrainingRoundSelection(
    val trainingJobId: UUID
)

object TrainingRoundTags {
    const val TRAINING_JOB_ID = "trainingJobId"
}

object TrainingRoundMetadata {
    val concepts = listOf("TrainingRound")
}

package tech.medo.trainingorchestration.traininground

data class TrainingRoundTrainingJobIdPreviousRoundNumberSelection(
    val normalizedTrainingJobId: String,
    val normalizedPreviousRoundNumber: String
)

object TrainingRoundTrainingJobIdPreviousRoundNumberReservationTags {
    const val TRAINING_JOB_ID = "trainingJobId"
    const val PREVIOUS_ROUND_NUMBER = "previousRoundNumber"
}

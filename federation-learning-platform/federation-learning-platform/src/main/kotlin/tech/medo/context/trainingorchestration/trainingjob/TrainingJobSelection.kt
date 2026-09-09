package tech.medo.trainingorchestration.trainingjob

import java.util.UUID;


data class TrainingJobSelection(
    val trainingJobId: UUID
)

object TrainingJobTags {
    const val TRAINING_JOB_ID = "trainingJobId"
}

object TrainingJobMetadata {
    val concepts = listOf("TrainingJob")
}

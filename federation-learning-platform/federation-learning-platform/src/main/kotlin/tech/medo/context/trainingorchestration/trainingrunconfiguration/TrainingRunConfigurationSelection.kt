package tech.medo.trainingorchestration.trainingrunconfiguration

import java.util.UUID;


data class TrainingRunConfigurationSelection(
    val trainingRunConfigurationId: UUID
)

object TrainingRunConfigurationTags {
    const val TRAINING_RUN_CONFIGURATION_ID = "trainingRunConfigurationId"
}

object TrainingRunConfigurationMetadata {
    val concepts = listOf("TrainingRunConfiguration")
}

package tech.medo.runtimemonitoring.trainingalert

import java.util.UUID;


data class TrainingAlertSelection(
    val alertId: UUID
)

object TrainingAlertTags {
    const val ALERT_ID = "alertId"
}

object TrainingAlertMetadata {
    val concepts = listOf("TrainingAlert")
}

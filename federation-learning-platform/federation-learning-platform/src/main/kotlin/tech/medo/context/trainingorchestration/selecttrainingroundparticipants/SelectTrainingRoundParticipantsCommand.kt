package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;

import tech.medo.trainingorchestration.traininground.TrainingRoundTrainingJobIdPreviousRoundNumberSelection

@Command
data class SelectTrainingRoundParticipantsCommand(
    val trainingJobId: UUID,
    val roundId: UUID = java.util.UUID.randomUUID(),
    val previousRoundNumber: Int,
    val federationId: UUID,
    val federationName: String?,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val trainingJobObjective: String
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(roundId = roundId)


    val trainingRoundTrainingJobIdPreviousRoundNumberSelection: TrainingRoundTrainingJobIdPreviousRoundNumberSelection = TrainingRoundTrainingJobIdPreviousRoundNumberSelection(normalizedTrainingJobId = trainingJobId.toString().trim().lowercase(), normalizedPreviousRoundNumber = previousRoundNumber.toString().trim().lowercase())
}

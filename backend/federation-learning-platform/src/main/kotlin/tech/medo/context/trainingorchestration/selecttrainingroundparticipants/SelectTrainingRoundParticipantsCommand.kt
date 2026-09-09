package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;


@Command
data class SelectTrainingRoundParticipantsCommand(
    val trainingJobId: UUID
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

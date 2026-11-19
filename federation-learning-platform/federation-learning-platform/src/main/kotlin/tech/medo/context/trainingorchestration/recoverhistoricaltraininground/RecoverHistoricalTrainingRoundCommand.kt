package tech.medo.trainingorchestration.recoverhistoricaltraininground

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;


@Command
data class RecoverHistoricalTrainingRoundCommand(
    val roundId: UUID,
    val trainingJobId: UUID,
    val recoveryDigest: String
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(roundId = roundId)


}

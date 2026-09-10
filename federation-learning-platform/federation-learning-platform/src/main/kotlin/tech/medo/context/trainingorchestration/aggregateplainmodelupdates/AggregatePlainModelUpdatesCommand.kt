package tech.medo.trainingorchestration.aggregateplainmodelupdates

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class AggregatePlainModelUpdatesCommand(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String,
    val aggregatedModelId: UUID = java.util.UUID.randomUUID(),
    val modelUpdateArtifactRefs: List<String>
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

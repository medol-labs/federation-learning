package tech.medo.trainingorchestration.completesecureaggregation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;


@Command
data class CompleteSecureAggregationCommand(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val secureAggregationSessionId: UUID,
    val aggregatedModelVersionId: UUID,
    val modelFormat: String,
    val modelHash: String
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

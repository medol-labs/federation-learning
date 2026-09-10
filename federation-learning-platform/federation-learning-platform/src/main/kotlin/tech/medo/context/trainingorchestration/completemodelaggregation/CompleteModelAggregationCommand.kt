package tech.medo.trainingorchestration.completemodelaggregation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class CompleteModelAggregationCommand(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val secureAggregationSessionId: UUID?,
    val aggregatedModelId: UUID,
    val aggregatedModelName: String,
    val aggregatedModelVersion: String,
    val aggregatedModelDescription: String?,
    val modelSourceType: String,
    val aggregatedModelArtifactUri: String,
    val aggregatedModelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val aggregatedModelSignatureUri: String?,
    val aggregatedModelSizeBytes: Int?
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

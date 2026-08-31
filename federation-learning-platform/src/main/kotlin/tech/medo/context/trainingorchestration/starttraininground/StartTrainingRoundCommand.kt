package tech.medo.trainingorchestration.starttraininground

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class StartTrainingRoundCommand(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val publicKeyRef: String?,
    val encryptedParameterScale: Int?
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

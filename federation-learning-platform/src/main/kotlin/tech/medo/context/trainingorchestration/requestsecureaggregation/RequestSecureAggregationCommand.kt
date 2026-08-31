package tech.medo.trainingorchestration.requestsecureaggregation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class RequestSecureAggregationCommand(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val requiredParticipantCount: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val secureAggregationRequired: Boolean
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

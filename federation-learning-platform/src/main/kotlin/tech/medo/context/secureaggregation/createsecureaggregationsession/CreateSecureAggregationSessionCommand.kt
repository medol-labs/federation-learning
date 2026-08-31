package tech.medo.secureaggregation.createsecureaggregationsession

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class CreateSecureAggregationSessionCommand(
    val secureAggregationSessionId: UUID = java.util.UUID.randomUUID(),
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
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

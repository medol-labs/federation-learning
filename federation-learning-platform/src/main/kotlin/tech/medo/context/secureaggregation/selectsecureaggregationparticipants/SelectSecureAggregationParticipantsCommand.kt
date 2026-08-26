package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class SelectSecureAggregationParticipantsCommand(
    val secureAggregationSessionId: UUID,
    val roundId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedParticipantCount: Int,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

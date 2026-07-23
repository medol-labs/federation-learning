package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class SelectSecureAggregationParticipantsCommand(
    val secureAggregationSessionId: UUID,
    val roundId: UUID,
    val acceptedRuntimeIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipantCount: Int
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

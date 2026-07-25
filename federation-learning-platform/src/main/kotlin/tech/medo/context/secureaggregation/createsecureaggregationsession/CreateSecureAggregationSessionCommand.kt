package tech.medo.secureaggregation.createsecureaggregationsession

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class CreateSecureAggregationSessionCommand(
    val secureAggregationSessionId: UUID = java.util.UUID.randomUUID(),
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val requiredParticipantCount: Int,
    val acceptedRuntimeIds: List<UUID>
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

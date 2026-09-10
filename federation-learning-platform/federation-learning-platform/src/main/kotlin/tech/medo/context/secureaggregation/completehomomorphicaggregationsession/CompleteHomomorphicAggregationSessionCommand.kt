package tech.medo.secureaggregation.completehomomorphicaggregationsession

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class CompleteHomomorphicAggregationSessionCommand(
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregatedModelId: UUID = java.util.UUID.randomUUID(),
    val encryptedUpdateArtifactRefs: List<String>
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

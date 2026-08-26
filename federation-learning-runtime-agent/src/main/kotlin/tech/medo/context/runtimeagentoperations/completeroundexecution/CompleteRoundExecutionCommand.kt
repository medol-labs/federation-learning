package tech.medo.runtimeagentoperations.completeroundexecution

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class CompleteRoundExecutionCommand(
    val roundExecutionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val runtimeEngineJobId: String,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val localUpdateArtifactRef: String?,
    val encryptedUpdateArtifactRef: String?,
    val encryptedUpdateDigest: String?,
    val metricsArtifactRef: String?,
    val trainingLoss: BigDecimal?
) {
    @TargetEntityId
    val selection: RoundExecutionSelection = RoundExecutionSelection(executionPlanId = executionPlanId)

}

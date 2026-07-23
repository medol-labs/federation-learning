package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class SubmitAgentLocalModelUpdateCommand(
    val modelUpdateSubmissionId: UUID = java.util.UUID.randomUUID(),
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val roundExecutionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val localModelVersionId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val trainingLoss: BigDecimal
) {
    @TargetEntityId
    val selection: RoundExecutionSelection = RoundExecutionSelection(executionPlanId = executionPlanId)

}

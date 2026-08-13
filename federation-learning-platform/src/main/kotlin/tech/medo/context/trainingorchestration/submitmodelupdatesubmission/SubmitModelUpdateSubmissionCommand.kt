package tech.medo.trainingorchestration.submitmodelupdatesubmission

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class SubmitModelUpdateSubmissionCommand(
    val modelUpdateSubmissionId: UUID = java.util.UUID.randomUUID(),
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundExecutionId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val trainingLoss: BigDecimal
) {
    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)

}

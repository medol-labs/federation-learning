package tech.medo.modellifecycle.registercandidatemodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class RegisterCandidateModelCommand(
    val modelId: UUID,
    val trainingJobId: UUID,
    val trainingJobObjective: String,
    val finalRoundId: UUID,
    val modelArtifactId: UUID,
    val modelArtifactDigest: String,
    val evaluationReportId: UUID,
    val finalGlobalAccuracy: BigDecimal
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}

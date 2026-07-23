package tech.medo.modellifecycle.registercandidatemodel

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class RegisterCandidateModelCommand(
    val modelVersionId: UUID,
    val trainingJobId: UUID,
    val finalRoundId: UUID,
    val modelArtifactId: UUID,
    val modelHash: String,
    val evaluationReportId: UUID,
    val finalGlobalAccuracy: BigDecimal
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}

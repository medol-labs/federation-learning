package tech.medo.modellifecycle.recordmodelevaluationpackage

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.model.ModelSelection
import java.util.UUID;


@Command
data class RecordModelEvaluationPackageCommand(
    val modelId: UUID,
    val trainingJobId: UUID,
    val evaluationReportId: UUID,
    val experimentId: UUID,
    val hyperparameterSnapshotId: UUID,
    val reproducibilityManifestId: UUID,
    val modelCardId: UUID,
    val baselineModelId: UUID?
) {
    @TargetEntityId
    val selection: ModelSelection = ModelSelection(modelId = modelId)

}

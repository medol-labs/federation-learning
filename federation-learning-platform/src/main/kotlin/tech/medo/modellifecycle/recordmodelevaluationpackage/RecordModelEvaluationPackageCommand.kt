package tech.medo.modellifecycle.recordmodelevaluationpackage

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.modellifecycle.modelversion.ModelVersionSelection
import java.util.UUID;


@Command
data class RecordModelEvaluationPackageCommand(
    val modelVersionId: UUID,
    val trainingJobId: UUID,
    val evaluationReportId: UUID,
    val experimentId: UUID,
    val hyperparameterSnapshotId: UUID,
    val reproducibilityManifestId: UUID,
    val modelCardId: UUID,
    val baselineModelVersionId: UUID?
) {
    @TargetEntityId
    val selection: ModelVersionSelection = ModelVersionSelection(modelVersionId = modelVersionId)

}

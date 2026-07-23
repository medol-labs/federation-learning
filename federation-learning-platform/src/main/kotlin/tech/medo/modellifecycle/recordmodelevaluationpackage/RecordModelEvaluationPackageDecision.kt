package tech.medo.modellifecycle.recordmodelevaluationpackage

import org.springframework.stereotype.Component
import tech.medo.modellifecycle.recordmodelevaluationpackage.RecordModelEvaluationPackageCommand

import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState


import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum


@Component
class RecordModelEvaluationPackageDecision {
    fun decide(command: RecordModelEvaluationPackageCommand, state: ModelVersionState): List<Any> {
        require(state.currentState == ModelVersionStateEnum.CANDIDATE) {
            "RecordModelEvaluationPackage requires ModelVersion to be Candidate."
        }
        return listOf(
            ModelEvaluationPackageRecordedEvent(modelVersionId = command.modelVersionId, trainingJobId = command.trainingJobId, evaluationReportId = command.evaluationReportId, experimentId = command.experimentId, hyperparameterSnapshotId = command.hyperparameterSnapshotId, reproducibilityManifestId = command.reproducibilityManifestId, modelCardId = command.modelCardId, baselineModelVersionId = command.baselineModelVersionId)
        )
    }
}

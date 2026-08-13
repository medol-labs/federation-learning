package tech.medo.modellifecycle.recordmodelevaluationpackage

import tech.medo.modellifecycle.recordmodelevaluationpackage.RecordModelEvaluationPackageCommand

import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface RecordModelEvaluationPackageDecision {
    fun decide(command: RecordModelEvaluationPackageCommand, state: ModelState): List<Any> {
        require(state.currentState == ModelStateEnum.CANDIDATE) {
            "RecordModelEvaluationPackage requires Model to be Candidate."
        }
        return listOf(
            ModelEvaluationPackageRecordedEvent(modelId = command.modelId, trainingJobId = command.trainingJobId, evaluationReportId = command.evaluationReportId, experimentId = command.experimentId, hyperparameterSnapshotId = command.hyperparameterSnapshotId, reproducibilityManifestId = command.reproducibilityManifestId, modelCardId = command.modelCardId, baselineModelId = command.baselineModelId)
        )
    }
}

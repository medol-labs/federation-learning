package tech.medo.modellifecycle.approvemodel

import tech.medo.modellifecycle.approvemodel.ApproveModelCommand

import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState


import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum


interface ApproveModelDecision {
    fun decide(command: ApproveModelCommand, state: ModelVersionState): List<Any> {
        require(state.currentState == ModelVersionStateEnum.EVALUATION_PACKAGED) {
            "ApproveModel requires ModelVersion to be EvaluationPackaged."
        }
        return listOf(
            ModelApprovedEvent(modelVersionId = command.modelVersionId, approvalNote = command.approvalNote)
        )
    }
}

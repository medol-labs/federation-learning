package tech.medo.modellifecycle.approvemodel

import tech.medo.modellifecycle.approvemodel.ApproveModelCommand


import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface ApproveModelDecision {
    fun decide(command: ApproveModelCommand, state: ModelState): List<Any> {
        require(state.currentState == ModelStateEnum.EVALUATION_PACKAGED) {
            "ApproveModel requires Model to be EvaluationPackaged."
        }
        return listOf(
            ModelApprovedEvent(modelId = command.modelId, approvalNote = command.approvalNote)
        )
    }
}

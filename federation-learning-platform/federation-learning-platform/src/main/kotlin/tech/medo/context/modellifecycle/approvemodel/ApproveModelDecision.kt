package tech.medo.modellifecycle.approvemodel

import tech.medo.modellifecycle.approvemodel.ApproveModelCommand


import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface ApproveModelDecision {
    fun decide(command: ApproveModelCommand, state: ModelState): List<Any> {
        if (state.currentState != ModelStateEnum.EvaluationPackaged) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.modellifecycle.approveModel.requiresState",
                args = mapOf(
                    "command" to "ApproveModel",
                    "aggregate" to "Model",
                    "expectedState" to "EvaluationPackaged",
                    "actualState" to state.currentState.toString()
                ),
                message = "ApproveModel requires Model to be EvaluationPackaged."
            )
        }
        return listOf(
            ModelApprovedEvent(modelId = command.modelId, approvalNote = command.approvalNote)
        )
    }
}

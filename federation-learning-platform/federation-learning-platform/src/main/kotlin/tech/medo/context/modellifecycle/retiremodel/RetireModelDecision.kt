package tech.medo.modellifecycle.retiremodel

import tech.medo.modellifecycle.retiremodel.RetireModelCommand


import tech.medo.modellifecycle.events.ModelRetiredEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface RetireModelDecision {
    fun decide(command: RetireModelCommand, state: ModelState): List<Any> {
        if (state.currentState != ModelStateEnum.Production) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.modellifecycle.retireModel.requiresState",
                args = mapOf(
                    "command" to "RetireModel",
                    "aggregate" to "Model",
                    "expectedState" to "Production",
                    "actualState" to state.currentState.toString()
                ),
                message = "RetireModel requires Model to be Production."
            )
        }
        return listOf(
            ModelRetiredEvent(modelId = command.modelId, retirementReason = command.retirementReason)
        )
    }
}

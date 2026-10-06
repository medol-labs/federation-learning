package tech.medo.modellifecycle.rollbackmodel

import tech.medo.modellifecycle.rollbackmodel.RollbackModelCommand


import tech.medo.modellifecycle.events.ModelRolledBackEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface RollbackModelDecision {
    fun decide(command: RollbackModelCommand, state: ModelState): List<Any> {
        if (state.currentState != ModelStateEnum.Production) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.modellifecycle.rollbackModel.requiresState",
                args = mapOf(
                    "command" to "RollbackModel",
                    "aggregate" to "Model",
                    "expectedState" to "Production",
                    "actualState" to state.currentState.toString()
                ),
                message = "RollbackModel requires Model to be Production."
            )
        }
        return listOf(
            ModelRolledBackEvent(modelId = command.modelId, previousModelId = command.previousModelId, rollbackReason = command.rollbackReason)
        )
    }
}

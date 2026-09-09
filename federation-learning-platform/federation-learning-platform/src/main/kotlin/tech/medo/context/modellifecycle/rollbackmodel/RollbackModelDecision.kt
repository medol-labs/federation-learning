package tech.medo.modellifecycle.rollbackmodel

import tech.medo.modellifecycle.rollbackmodel.RollbackModelCommand


import tech.medo.modellifecycle.events.ModelRolledBackEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface RollbackModelDecision {
    fun decide(command: RollbackModelCommand, state: ModelState): List<Any> {
        require(state.currentState == ModelStateEnum.PRODUCTION) {
            "RollbackModel requires Model to be Production."
        }
        return listOf(
            ModelRolledBackEvent(modelId = command.modelId, previousModelId = command.previousModelId, rollbackReason = command.rollbackReason)
        )
    }
}

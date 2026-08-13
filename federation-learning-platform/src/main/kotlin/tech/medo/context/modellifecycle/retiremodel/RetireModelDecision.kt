package tech.medo.modellifecycle.retiremodel

import tech.medo.modellifecycle.retiremodel.RetireModelCommand

import tech.medo.modellifecycle.events.ModelRetiredEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface RetireModelDecision {
    fun decide(command: RetireModelCommand, state: ModelState): List<Any> {
        require(state.currentState == ModelStateEnum.PRODUCTION) {
            "RetireModel requires Model to be Production."
        }
        return listOf(
            ModelRetiredEvent(modelId = command.modelId, retirementReason = command.retirementReason)
        )
    }
}

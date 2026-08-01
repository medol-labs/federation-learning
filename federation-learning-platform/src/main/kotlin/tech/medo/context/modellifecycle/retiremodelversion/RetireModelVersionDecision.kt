package tech.medo.modellifecycle.retiremodelversion

import tech.medo.modellifecycle.retiremodelversion.RetireModelVersionCommand

import tech.medo.modellifecycle.events.ModelVersionRetiredEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState


import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum


interface RetireModelVersionDecision {
    fun decide(command: RetireModelVersionCommand, state: ModelVersionState): List<Any> {
        require(state.currentState == ModelVersionStateEnum.PRODUCTION) {
            "RetireModelVersion requires ModelVersion to be Production."
        }
        return listOf(
            ModelVersionRetiredEvent(modelVersionId = command.modelVersionId, retirementReason = command.retirementReason)
        )
    }
}

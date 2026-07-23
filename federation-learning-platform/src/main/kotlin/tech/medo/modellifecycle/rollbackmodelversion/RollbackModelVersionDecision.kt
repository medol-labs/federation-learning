package tech.medo.modellifecycle.rollbackmodelversion

import org.springframework.stereotype.Component
import tech.medo.modellifecycle.rollbackmodelversion.RollbackModelVersionCommand

import tech.medo.modellifecycle.events.ModelVersionRolledBackEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState


import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum


@Component
class RollbackModelVersionDecision {
    fun decide(command: RollbackModelVersionCommand, state: ModelVersionState): List<Any> {
        require(state.currentState == ModelVersionStateEnum.PRODUCTION) {
            "RollbackModelVersion requires ModelVersion to be Production."
        }
        return listOf(
            ModelVersionRolledBackEvent(modelVersionId = command.modelVersionId, previousModelVersionId = command.previousModelVersionId, rollbackReason = command.rollbackReason)
        )
    }
}

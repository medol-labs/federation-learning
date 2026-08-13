package tech.medo.modellifecycle.promotemodeltoproduction

import tech.medo.modellifecycle.promotemodeltoproduction.PromoteModelToProductionCommand

import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface PromoteModelToProductionDecision {
    fun decide(command: PromoteModelToProductionCommand, state: ModelState): List<Any> {
        require(state.currentState == ModelStateEnum.APPROVED) {
            "PromoteModelToProduction requires Model to be Approved."
        }
        return listOf(
            ModelPromotedToProductionEvent(modelId = command.modelId, releaseChannel = command.releaseChannel, productionStage = command.productionStage)
        )
    }
}

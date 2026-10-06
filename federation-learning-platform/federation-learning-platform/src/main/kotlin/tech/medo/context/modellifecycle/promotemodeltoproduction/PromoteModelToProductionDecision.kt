package tech.medo.modellifecycle.promotemodeltoproduction

import tech.medo.modellifecycle.promotemodeltoproduction.PromoteModelToProductionCommand


import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.model.ModelState


import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface PromoteModelToProductionDecision {
    fun decide(command: PromoteModelToProductionCommand, state: ModelState): List<Any> {
        if (state.currentState != ModelStateEnum.Approved) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.modellifecycle.promoteModelToProduction.requiresState",
                args = mapOf(
                    "command" to "PromoteModelToProduction",
                    "aggregate" to "Model",
                    "expectedState" to "Approved",
                    "actualState" to state.currentState.toString()
                ),
                message = "PromoteModelToProduction requires Model to be Approved."
            )
        }
        return listOf(
            ModelPromotedToProductionEvent(modelId = command.modelId, releaseChannel = command.releaseChannel, productionStage = command.productionStage)
        )
    }
}

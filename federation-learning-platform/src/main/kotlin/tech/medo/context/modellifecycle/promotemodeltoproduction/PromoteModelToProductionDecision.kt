package tech.medo.modellifecycle.promotemodeltoproduction

import org.springframework.stereotype.Component
import tech.medo.modellifecycle.promotemodeltoproduction.PromoteModelToProductionCommand

import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState


import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum


@Component
class PromoteModelToProductionDecision {
    fun decide(command: PromoteModelToProductionCommand, state: ModelVersionState): List<Any> {
        require(state.currentState == ModelVersionStateEnum.APPROVED) {
            "PromoteModelToProduction requires ModelVersion to be Approved."
        }
        return listOf(
            ModelPromotedToProductionEvent(modelVersionId = command.modelVersionId, releaseChannel = command.releaseChannel, productionStage = command.productionStage)
        )
    }
}

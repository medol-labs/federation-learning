package tech.medo.runtimeagentoperations.approvedatasetfortraining

import tech.medo.runtimeagentoperations.approvedatasetfortraining.ApproveDatasetForTrainingCommand


import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface ApproveDatasetForTrainingDecision {
    fun decide(command: ApproveDatasetForTrainingCommand, state: DatasetState): List<Any> {
        if (state.currentState != DatasetStateEnum.ContractValidationCompleted) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.approveDatasetForTraining.requiresState",
                args = mapOf(
                    "command" to "ApproveDatasetForTraining",
                    "aggregate" to "Dataset",
                    "expectedState" to "ContractValidationCompleted",
                    "actualState" to state.currentState.toString()
                ),
                message = "ApproveDatasetForTraining requires Dataset to be ContractValidationCompleted."
            )
        }
        return listOf(
            DatasetApprovedForTrainingEvent(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName)
        )
    }
}

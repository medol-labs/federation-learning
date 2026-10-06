package tech.medo.runtimeagentoperations.rejectdatasetfortraining

import tech.medo.runtimeagentoperations.rejectdatasetfortraining.RejectDatasetForTrainingCommand


import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface RejectDatasetForTrainingDecision {
    fun decide(command: RejectDatasetForTrainingCommand, state: DatasetState): List<Any> {
        if (state.currentState != DatasetStateEnum.ContractValidationCompleted) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.rejectDatasetForTraining.requiresState",
                args = mapOf(
                    "command" to "RejectDatasetForTraining",
                    "aggregate" to "Dataset",
                    "expectedState" to "ContractValidationCompleted",
                    "actualState" to state.currentState.toString()
                ),
                message = "RejectDatasetForTraining requires Dataset to be ContractValidationCompleted."
            )
        }
        return listOf(
            DatasetRejectedForTrainingEvent(datasetId = command.datasetId, rejectionReason = command.rejectionReason, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName)
        )
    }
}

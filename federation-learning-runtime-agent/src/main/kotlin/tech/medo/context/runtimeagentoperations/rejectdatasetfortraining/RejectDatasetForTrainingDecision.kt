package tech.medo.runtimeagentoperations.rejectdatasetfortraining

import tech.medo.runtimeagentoperations.rejectdatasetfortraining.RejectDatasetForTrainingCommand

import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface RejectDatasetForTrainingDecision {
    fun decide(command: RejectDatasetForTrainingCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            "RejectDatasetForTraining requires Dataset to be ContractValidationCompleted."
        }
        return listOf(
            DatasetRejectedForTrainingEvent(datasetId = command.datasetId, rejectionReason = command.rejectionReason, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName)
        )
    }
}

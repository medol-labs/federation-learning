package tech.medo.runtimeagentoperations.approvedatasetfortraining

import tech.medo.runtimeagentoperations.approvedatasetfortraining.ApproveDatasetForTrainingCommand


import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface ApproveDatasetForTrainingDecision {
    fun decide(command: ApproveDatasetForTrainingCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            "ApproveDatasetForTraining requires Dataset to be ContractValidationCompleted."
        }
        return listOf(
            DatasetApprovedForTrainingEvent(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName)
        )
    }
}

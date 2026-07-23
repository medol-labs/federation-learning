package tech.medo.datasetgovernance.rejectdatasetfortraining

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.rejectdatasetfortraining.RejectDatasetForTrainingCommand

import tech.medo.datasetgovernance.events.DatasetRejectedForTrainingEvent
import tech.medo.datasetgovernance.dataset.DatasetState


import tech.medo.datasetgovernance.domain.states.DatasetStateEnum


@Component
class RejectDatasetForTrainingDecision {
    fun decide(command: RejectDatasetForTrainingCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            "RejectDatasetForTraining requires Dataset to be ContractValidationCompleted."
        }
        return listOf(
            DatasetRejectedForTrainingEvent(datasetId = command.datasetId, rejectionReason = command.rejectionReason)
        )
    }
}

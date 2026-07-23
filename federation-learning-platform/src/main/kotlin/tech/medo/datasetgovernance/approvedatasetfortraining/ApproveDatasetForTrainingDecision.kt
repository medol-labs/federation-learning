package tech.medo.datasetgovernance.approvedatasetfortraining

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.approvedatasetfortraining.ApproveDatasetForTrainingCommand

import tech.medo.datasetgovernance.events.DatasetApprovedForTrainingEvent
import tech.medo.datasetgovernance.dataset.DatasetState


import tech.medo.datasetgovernance.domain.states.DatasetStateEnum


@Component
class ApproveDatasetForTrainingDecision {
    fun decide(command: ApproveDatasetForTrainingCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            "ApproveDatasetForTraining requires Dataset to be ContractValidationCompleted."
        }
        return listOf(
            DatasetApprovedForTrainingEvent(datasetId = command.datasetId, organizationId = command.organizationId)
        )
    }
}

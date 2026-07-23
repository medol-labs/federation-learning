package tech.medo.datasetgovernance.revokedatasettrainingapproval

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.revokedatasettrainingapproval.RevokeDatasetTrainingApprovalCommand

import tech.medo.datasetgovernance.events.DatasetTrainingApprovalRevokedEvent
import tech.medo.datasetgovernance.dataset.DatasetState


import tech.medo.datasetgovernance.domain.states.DatasetStateEnum


@Component
class RevokeDatasetTrainingApprovalDecision {
    fun decide(command: RevokeDatasetTrainingApprovalCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.APPROVED) {
            "RevokeDatasetTrainingApproval requires Dataset to be Approved."
        }
        return listOf(
            DatasetTrainingApprovalRevokedEvent(datasetId = command.datasetId, revokeReason = command.revokeReason)
        )
    }
}

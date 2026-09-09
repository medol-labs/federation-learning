package tech.medo.runtimeagentoperations.revokedatasettrainingapproval

import tech.medo.runtimeagentoperations.revokedatasettrainingapproval.RevokeDatasetTrainingApprovalCommand


import tech.medo.runtimeagentoperations.events.DatasetTrainingApprovalRevokedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface RevokeDatasetTrainingApprovalDecision {
    fun decide(command: RevokeDatasetTrainingApprovalCommand, state: DatasetState): List<Any> {
        require(state.currentState == DatasetStateEnum.APPROVED) {
            "RevokeDatasetTrainingApproval requires Dataset to be Approved."
        }
        return listOf(
            DatasetTrainingApprovalRevokedEvent(datasetId = command.datasetId, revokeReason = command.revokeReason, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName)
        )
    }
}

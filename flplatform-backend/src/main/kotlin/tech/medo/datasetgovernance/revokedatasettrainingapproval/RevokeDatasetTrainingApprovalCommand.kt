package tech.medo.datasetgovernance.revokedatasettrainingapproval

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.dataset.DatasetSelection
import java.util.UUID;


@Command
data class RevokeDatasetTrainingApprovalCommand(
    val datasetId: UUID,
    val revokeReason: String,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String
) {
    @TargetEntityId
    val selection: DatasetSelection = DatasetSelection(organizationId = organizationId, featureSchemaId = featureSchemaId, datasetName = datasetName)

}

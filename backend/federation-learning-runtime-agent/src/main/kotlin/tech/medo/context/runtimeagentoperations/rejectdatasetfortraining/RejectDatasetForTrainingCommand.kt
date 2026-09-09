package tech.medo.runtimeagentoperations.rejectdatasetfortraining

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.dataset.DatasetSelection
import java.util.UUID;


@Command
data class RejectDatasetForTrainingCommand(
    val datasetId: UUID,
    val rejectionReason: String,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String
) {
    @TargetEntityId
    val selection: DatasetSelection = DatasetSelection(organizationId = organizationId, featureSchemaId = featureSchemaId, datasetName = datasetName)

}

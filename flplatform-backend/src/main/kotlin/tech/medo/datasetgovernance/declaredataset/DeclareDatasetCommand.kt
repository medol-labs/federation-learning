package tech.medo.datasetgovernance.declaredataset

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.dataset.DatasetSelection
import java.util.UUID;


@Command
data class DeclareDatasetCommand(
    val datasetId: UUID = java.util.UUID.randomUUID(),
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String,
    val datasetType: String,
    val datasetUsage: String
) {
    @TargetEntityId
    val selection: DatasetSelection = DatasetSelection(organizationId = organizationId, featureSchemaId = featureSchemaId, datasetName = datasetName)

}

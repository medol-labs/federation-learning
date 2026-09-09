package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.dataset.DatasetSelection
import java.util.UUID;


@Command
data class RetryDatasetContractValidationCommand(
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String
) {
    @TargetEntityId
    val selection: DatasetSelection = DatasetSelection(organizationId = organizationId, featureSchemaId = featureSchemaId, datasetName = datasetName)

}

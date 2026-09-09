package tech.medo.runtimeagentoperations.validatedatasetcontract

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.dataset.DatasetSelection
import java.util.UUID;


@Command
data class ValidateDatasetContractCommand(
    val datasetId: UUID,
    val metadataReportId: UUID,
    val featureSchemaId: UUID,
    val organizationId: UUID,
    val datasetName: String
) {
    @TargetEntityId
    val selection: DatasetSelection = DatasetSelection(organizationId = organizationId, featureSchemaId = featureSchemaId, datasetName = datasetName)

}

package tech.medo.runtimeagentoperations.profileagentdataset

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.runtimedatasetmetadata.RuntimeDatasetMetadataSelection
import java.util.UUID;


@Command
data class ProfileAgentDatasetCommand(
    val metadataReportId: UUID = java.util.UUID.randomUUID(),
    val runtimeDatasetBindingId: UUID
) {
    @TargetEntityId
    val selection: RuntimeDatasetMetadataSelection = RuntimeDatasetMetadataSelection(metadataReportId = metadataReportId)

}

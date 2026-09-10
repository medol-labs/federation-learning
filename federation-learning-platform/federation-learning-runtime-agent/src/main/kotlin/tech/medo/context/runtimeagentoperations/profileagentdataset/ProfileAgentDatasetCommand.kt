package tech.medo.runtimeagentoperations.profileagentdataset

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileSelection
import java.util.UUID;


@Command
data class ProfileAgentDatasetCommand(
    val metadataReportId: UUID = java.util.UUID.randomUUID(),
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    val runtimeId: UUID,
    val runtimeName: String?
) {
    @TargetEntityId
    val selection: AgentDatasetProfileSelection = AgentDatasetProfileSelection(runtimeDatasetBindingId = runtimeDatasetBindingId)

}

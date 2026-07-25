package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileSelection
import java.util.UUID;


@Command
data class ReprofileAgentDatasetCommand(
    val metadataReportId: UUID = java.util.UUID.randomUUID(),
    val runtimeDatasetBindingId: UUID
) {
    @TargetEntityId
    val selection: AgentDatasetProfileSelection = AgentDatasetProfileSelection(metadataReportId = metadataReportId)

}

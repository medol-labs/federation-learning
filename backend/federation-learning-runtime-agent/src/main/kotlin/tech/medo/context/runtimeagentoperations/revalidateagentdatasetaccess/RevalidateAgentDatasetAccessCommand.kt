package tech.medo.runtimeagentoperations.revalidateagentdatasetaccess

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidation.AgentDatasetAccessValidationSelection
import java.util.UUID;


@Command
data class RevalidateAgentDatasetAccessCommand(
    val datasetAccessValidationId: UUID = java.util.UUID.randomUUID(),
    val runtimeDatasetBindingId: UUID
) {
    @TargetEntityId
    val selection: AgentDatasetAccessValidationSelection = AgentDatasetAccessValidationSelection(datasetAccessValidationId = datasetAccessValidationId)

}

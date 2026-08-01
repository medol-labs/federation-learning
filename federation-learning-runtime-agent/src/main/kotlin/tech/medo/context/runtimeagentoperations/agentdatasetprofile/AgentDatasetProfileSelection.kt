package tech.medo.runtimeagentoperations.agentdatasetprofile

import java.util.UUID;


data class AgentDatasetProfileSelection(
    val runtimeDatasetBindingId: UUID
)

object AgentDatasetProfileTags {
    const val RUNTIME_DATASET_BINDING_ID = "runtimeDatasetBindingId"
}

object AgentDatasetProfileMetadata {
    val concepts = listOf("AgentDatasetProfile")
}

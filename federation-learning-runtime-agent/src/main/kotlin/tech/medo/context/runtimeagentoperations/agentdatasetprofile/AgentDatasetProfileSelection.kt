package tech.medo.runtimeagentoperations.agentdatasetprofile

import java.util.UUID;


data class AgentDatasetProfileSelection(
    val metadataReportId: UUID
)

object AgentDatasetProfileTags {
    const val METADATA_REPORT_ID = "metadataReportId"
}

object AgentDatasetProfileMetadata {
    val concepts = listOf("AgentDatasetProfile")
}

package tech.medo.runtimeagentoperations.profileagentdataset

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand

import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState





@Component
class ProfileAgentDatasetDecision {
    fun decide(command: ProfileAgentDatasetCommand): List<Any> {
        return listOf(
            AgentDatasetMetadataReportedEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = java.util.UUID.randomUUID() /* TODO: derive value */, organizationId = java.util.UUID.randomUUID() /* TODO: derive value */, runtimeId = java.util.UUID.randomUUID() /* TODO: derive value */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: derive value */, sampleCount = 0 /* TODO: derive value */, featureCount = 0 /* TODO: derive value */, schemaCompatible = null /* TODO: derive value */, labelCompatible = null /* TODO: derive value */, missingValueRate = null /* TODO: derive value */, duplicateRate = null /* TODO: derive value */, qualityScore = null /* TODO: derive value */, nonIidScore = null /* TODO: derive value */, classBalanceScore = null /* TODO: derive value */)
        )
    }
}

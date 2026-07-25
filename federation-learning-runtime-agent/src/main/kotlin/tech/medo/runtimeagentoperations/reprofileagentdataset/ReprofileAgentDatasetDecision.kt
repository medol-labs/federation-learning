package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand

import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState


import tech.medo.runtimeagentoperations.domain.states.AgentDatasetProfileStateEnum


@Component
class ReprofileAgentDatasetDecision {
    fun decide(command: ReprofileAgentDatasetCommand, state: AgentDatasetProfileState): List<Any> {
        require(state.currentState == AgentDatasetProfileStateEnum.REPORTED) {
            "ReprofileAgentDataset requires AgentDatasetProfile to be Reported."
        }
        return listOf(
            AgentDatasetReprofiledEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = java.util.UUID.randomUUID() /* TODO: derive value */, organizationId = java.util.UUID.randomUUID() /* TODO: derive value */, runtimeId = java.util.UUID.randomUUID() /* TODO: derive value */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: derive value */, sampleCount = 0 /* TODO: derive value */, featureCount = 0 /* TODO: derive value */, schemaCompatible = null /* TODO: derive value */, labelCompatible = null /* TODO: derive value */, missingValueRate = null /* TODO: derive value */, duplicateRate = null /* TODO: derive value */, qualityScore = null /* TODO: derive value */, nonIidScore = null /* TODO: derive value */, classBalanceScore = null /* TODO: derive value */)
        )
    }
}

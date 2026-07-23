package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand

import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent
import tech.medo.runtimeagentoperations.runtimedatasetmetadata.RuntimeDatasetMetadataState


import tech.medo.runtimeagentoperations.domain.states.RuntimeDatasetMetadataStateEnum


@Component
class ReprofileAgentDatasetDecision {
    fun decide(command: ReprofileAgentDatasetCommand, state: RuntimeDatasetMetadataState): List<Any> {
        require(state.currentState == RuntimeDatasetMetadataStateEnum.REPORTED) {
            "ReprofileAgentDataset requires RuntimeDatasetMetadata to be Reported."
        }
        return listOf(
            AgentDatasetReprofiledEvent(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = java.util.UUID.randomUUID() /* TODO: derive value */, organizationId = java.util.UUID.randomUUID() /* TODO: derive value */, runtimeId = java.util.UUID.randomUUID() /* TODO: derive value */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: derive value */, sampleCount = 0 /* TODO: derive value */, featureCount = 0 /* TODO: derive value */, schemaCompatible = null /* TODO: derive value */, labelCompatible = null /* TODO: derive value */, missingValueRate = null /* TODO: derive value */, duplicateRate = null /* TODO: derive value */, qualityScore = null /* TODO: derive value */, nonIidScore = null /* TODO: derive value */, classBalanceScore = null /* TODO: derive value */)
        )
    }
}

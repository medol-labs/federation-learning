package tech.medo.runtimeagentoperations.validatedatasetcontract

import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState





interface ValidateDatasetContractDecision {
    fun decide(command: ValidateDatasetContractCommand, state: DatasetState, portResult: ValidateDatasetContractResult, now: java.time.LocalDateTime): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is ValidateDatasetContractResult.Succeeded -> listOf(DatasetContractValidatedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, organizationId = command.organizationId, datasetName = command.datasetName))
                    is ValidateDatasetContractResult.Rejected -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                    is ValidateDatasetContractResult.Unavailable -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                }
    }
}

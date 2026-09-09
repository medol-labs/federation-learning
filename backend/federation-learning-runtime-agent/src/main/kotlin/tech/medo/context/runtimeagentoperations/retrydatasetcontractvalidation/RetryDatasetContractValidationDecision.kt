package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationCommand

import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationResult
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidationFailedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState


import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum


interface RetryDatasetContractValidationDecision {
    fun decide(command: RetryDatasetContractValidationCommand, state: DatasetState, portResult: RetryDatasetContractValidationResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            "RetryDatasetContractValidation requires Dataset to be ContractValidationCompleted."
        }
        return when (portResult) {
                    is RetryDatasetContractValidationResult.Succeeded -> listOf(DatasetContractRevalidatedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = requireNotNull(state.metadataReportId) { "metadataReportId is required from state." }, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, organizationId = command.organizationId, datasetName = command.datasetName))
                    is RetryDatasetContractValidationResult.Rejected -> listOf(DatasetContractRevalidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = requireNotNull(state.metadataReportId) { "metadataReportId is required from state." }, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                    is RetryDatasetContractValidationResult.Unavailable -> listOf(DatasetContractRevalidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = requireNotNull(state.metadataReportId) { "metadataReportId is required from state." }, schemaCompatible = requireNotNull(state.schemaCompatible) { "schemaCompatible is required from state." }, labelCompatible = requireNotNull(state.labelCompatible) { "labelCompatible is required from state." }, qualityScore = requireNotNull(state.qualityScore) { "qualityScore is required from state." }, nonIidScore = requireNotNull(state.nonIidScore) { "nonIidScore is required from state." }, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                }
    }
}

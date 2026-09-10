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
                    is ValidateDatasetContractResult.Succeeded -> listOf(
            DatasetContractValidatedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = portResult.schemaCompatible, labelCompatible = portResult.labelCompatible, qualityScore = portResult.qualityScore, nonIidScore = portResult.nonIidScore, organizationId = command.organizationId, datasetName = command.datasetName)
            )
                    is ValidateDatasetContractResult.Rejected -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = portResult.schemaCompatible, labelCompatible = portResult.labelCompatible, qualityScore = portResult.qualityScore, nonIidScore = portResult.nonIidScore, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                    is ValidateDatasetContractResult.Unavailable -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = false /* TODO: provide schemaCompatible */, labelCompatible = false /* TODO: provide labelCompatible */, qualityScore = java.math.BigDecimal.ZERO /* TODO: provide qualityScore */, nonIidScore = java.math.BigDecimal.ZERO /* TODO: provide nonIidScore */, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                }
    }
}

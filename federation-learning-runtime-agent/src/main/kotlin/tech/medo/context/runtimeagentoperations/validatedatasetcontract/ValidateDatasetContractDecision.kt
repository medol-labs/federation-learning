package tech.medo.runtimeagentoperations.validatedatasetcontract

import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState
import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum
import java.math.BigDecimal





interface ValidateDatasetContractDecision {
    fun decide(command: ValidateDatasetContractCommand, state: DatasetState, portResult: ValidateDatasetContractResult, now: java.time.LocalDateTime): List<Any> {
        if (state.currentState == DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED) {
            return emptyList()
        }

        val schemaCompatible = when (portResult) {
            is ValidateDatasetContractResult.Succeeded -> portResult.schemaCompatible
            is ValidateDatasetContractResult.Rejected -> portResult.schemaCompatible
            is ValidateDatasetContractResult.Unavailable -> null
        } ?: command.schemaCompatible ?: state.schemaCompatible ?: false
        val labelCompatible = when (portResult) {
            is ValidateDatasetContractResult.Succeeded -> portResult.labelCompatible
            is ValidateDatasetContractResult.Rejected -> portResult.labelCompatible
            is ValidateDatasetContractResult.Unavailable -> null
        } ?: command.labelCompatible ?: state.labelCompatible ?: false
        val qualityScore = when (portResult) {
            is ValidateDatasetContractResult.Succeeded -> portResult.qualityScore
            is ValidateDatasetContractResult.Rejected -> portResult.qualityScore
            is ValidateDatasetContractResult.Unavailable -> null
        } ?: command.qualityScore ?: state.qualityScore ?: BigDecimal.ZERO
        val nonIidScore = when (portResult) {
            is ValidateDatasetContractResult.Succeeded -> portResult.nonIidScore
            is ValidateDatasetContractResult.Rejected -> portResult.nonIidScore
            is ValidateDatasetContractResult.Unavailable -> null
        } ?: command.nonIidScore ?: state.nonIidScore ?: BigDecimal.ZERO

        return when (portResult) {
                    is ValidateDatasetContractResult.Succeeded -> listOf(DatasetContractValidatedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = schemaCompatible, labelCompatible = labelCompatible, qualityScore = qualityScore, nonIidScore = nonIidScore, organizationId = command.organizationId, datasetName = command.datasetName))
                    is ValidateDatasetContractResult.Rejected -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = schemaCompatible, labelCompatible = labelCompatible, qualityScore = qualityScore, nonIidScore = nonIidScore, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                    is ValidateDatasetContractResult.Unavailable -> listOf(DatasetContractValidationFailedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = schemaCompatible, labelCompatible = labelCompatible, qualityScore = qualityScore, nonIidScore = nonIidScore, failureReason = portResult.failureReason, organizationId = command.organizationId, datasetName = command.datasetName))
                }
    }
}

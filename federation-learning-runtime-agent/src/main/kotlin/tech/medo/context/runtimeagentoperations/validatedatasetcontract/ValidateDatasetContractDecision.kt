package tech.medo.runtimeagentoperations.validatedatasetcontract

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand

import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState





@Component
class ValidateDatasetContractDecision {
    fun decide(command: ValidateDatasetContractCommand, state: DatasetState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            DatasetContractValidatedEvent(datasetId = command.datasetId, featureSchemaId = command.featureSchemaId, metadataReportId = command.metadataReportId, schemaCompatible = false /* TODO: derive value */, labelCompatible = false /* TODO: derive value */, qualityScore = java.math.BigDecimal.ZERO /* TODO: derive value */, nonIidScore = java.math.BigDecimal.ZERO /* TODO: derive value */)
        )
    }
}

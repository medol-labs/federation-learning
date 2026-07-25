package tech.medo.runtimeagentoperations.declaredataset

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState





@Component
class DeclareDatasetDecision {
    fun decide(command: DeclareDatasetCommand): List<Any> {
        return listOf(
            DatasetDeclaredEvent(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName, datasetType = command.datasetType, datasetUsage = command.datasetUsage)
        )
    }
}

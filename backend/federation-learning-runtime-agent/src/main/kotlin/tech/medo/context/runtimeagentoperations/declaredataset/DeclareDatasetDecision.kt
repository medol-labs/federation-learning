package tech.medo.runtimeagentoperations.declaredataset

import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand

import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetResult
import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState





interface DeclareDatasetDecision {
    fun decide(command: DeclareDatasetCommand, portResult: DeclareDatasetResult): List<Any> {
        return when (portResult) {
                    is DeclareDatasetResult.Succeeded -> listOf(DatasetDeclaredEvent(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName, datasetUsage = command.datasetUsage, features = portResult.features, labels = portResult.labels))
                }
    }
}

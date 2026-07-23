package tech.medo.datasetgovernance.declaredataset

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.declaredataset.DeclareDatasetCommand

import tech.medo.datasetgovernance.events.DatasetDeclaredEvent
import tech.medo.datasetgovernance.dataset.DatasetState





@Component
class DeclareDatasetDecision {
    fun decide(command: DeclareDatasetCommand): List<Any> {
        return listOf(
            DatasetDeclaredEvent(datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, datasetName = command.datasetName, datasetType = command.datasetType, datasetUsage = command.datasetUsage)
        )
    }
}

package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand


import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent
import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingState

import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingRuntimeIdDatasetIdReservationState



interface ConfigureRuntimeDatasetBindingDecision {
    fun decide(command: ConfigureRuntimeDatasetBindingCommand, runtimeDatasetBindingRuntimeIdDatasetIdReservation: RuntimeDatasetBindingRuntimeIdDatasetIdReservationState): List<Any> {
        require(!runtimeDatasetBindingRuntimeIdDatasetIdReservation.reserved) {
            "RuntimeId DatasetId already exists."
        }
        return listOf(
                        RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent(runtimeDatasetBindingId = command.runtimeDatasetBindingId, runtimeId = command.runtimeId, datasetId = command.datasetId, normalizedRuntimeId = command.runtimeId.toString().trim().lowercase(), normalizedDatasetId = command.datasetId.toString().trim().lowercase()),
            RuntimeDatasetBindingConfiguredEvent(runtimeDatasetBindingId = command.runtimeDatasetBindingId, datasetId = command.datasetId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, organizationName = command.organizationName, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, datasetName = command.datasetName, runtimeId = command.runtimeId, runtimeName = command.runtimeName, filePath = command.filePath, dataFormat = command.dataFormat)
        )
    }
}

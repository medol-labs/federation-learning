package tech.medo.runtimeagentoperations.runtimedatasetbinding

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingConfiguredEvent
import tech.medo.runtimeagentoperations.domain.states.RuntimeDatasetBindingStateEnum

import java.util.UUID;


@EventSourced(idType = RuntimeDatasetBindingSelection::class)
class RuntimeDatasetBindingState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: RuntimeDatasetBindingSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(RuntimeDatasetBindingTags.DATASET_ID, selection.datasetId.toString())),
                EventCriteria.havingTags(Tag.of(RuntimeDatasetBindingTags.RUNTIME_ID, selection.runtimeId.toString()))
        )
    }


    var currentState: RuntimeDatasetBindingStateEnum? = null
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var featureSchemaId: UUID? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var runtimeId: UUID? = null
    var runtimeName: String? = null
    var dataSourceType: String? = null
    var host: String? = null
    var port: Int? = null
    var url: String? = null
    var databaseName: String? = null
    var schemaName: String? = null
    var tableName: String? = null
    var filePath: String? = null
    var objectBucket: String? = null
    var objectPrefix: String? = null
    var dataFormat: String? = null
    var credentialSecretName: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeDatasetBindingConfiguredEvent): RuntimeDatasetBindingState = apply {
        currentState = RuntimeDatasetBindingStateEnum.CONFIGURED
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        organizationName = event.organizationName
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
        runtimeId = event.runtimeId
        runtimeName = event.runtimeName
        dataSourceType = event.dataSourceType
        host = event.host
        port = event.port
        url = event.url
        databaseName = event.databaseName
        schemaName = event.schemaName
        tableName = event.tableName
        filePath = event.filePath
        objectBucket = event.objectBucket
        objectPrefix = event.objectPrefix
        dataFormat = event.dataFormat
        credentialSecretName = event.credentialSecretName
    }
}

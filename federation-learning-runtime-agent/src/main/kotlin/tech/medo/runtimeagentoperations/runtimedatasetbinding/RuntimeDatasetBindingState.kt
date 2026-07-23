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


@EventSourced(idType = UUID::class, tagKey = RuntimeDatasetBindingTags.RUNTIME_DATASET_BINDING_ID)
class RuntimeDatasetBindingState @EntityCreator constructor() {

    var currentState: RuntimeDatasetBindingStateEnum? = null
    private var runtimeDatasetBindingId: UUID? = null
    private var datasetId: UUID? = null
    private var organizationId: UUID? = null
    private var runtimeId: UUID? = null
    private var dataSourceType: String? = null
    private var host: String? = null
    private var port: Int? = null
    private var url: String? = null
    private var databaseName: String? = null
    private var schemaName: String? = null
    private var tableName: String? = null
    private var filePath: String? = null
    private var objectBucket: String? = null
    private var objectPrefix: String? = null
    private var dataFormat: String? = null
    private var credentialSecretName: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeDatasetBindingConfiguredEvent): RuntimeDatasetBindingState = apply {
        currentState = RuntimeDatasetBindingStateEnum.CONFIGURED
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
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

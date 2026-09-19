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
    var filePath: String? = null
    var dataFormat: String? = null

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
        filePath = event.filePath
        dataFormat = event.dataFormat
    }
}

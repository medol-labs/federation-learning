package tech.medo.runtimeagentoperations.agentdatasetaccessvalidation

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent
import tech.medo.runtimeagentoperations.domain.states.AgentDatasetAccessValidationStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = AgentDatasetAccessValidationTags.DATASET_ACCESS_VALIDATION_ID)
class AgentDatasetAccessValidationState @EntityCreator constructor() {

    var currentState: AgentDatasetAccessValidationStateEnum? = null
    private var datasetAccessValidationId: UUID? = null
    private var runtimeDatasetBindingId: UUID? = null
    private var datasetId: UUID? = null
    private var runtimeId: UUID? = null
    private var readable: Boolean? = null
    private var schemaReadable: Boolean? = null
    private var sampleBatchReadable: Boolean? = null
    private var failureReason: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentDatasetAccessValidatedEvent): AgentDatasetAccessValidationState = apply {
        currentState = AgentDatasetAccessValidationStateEnum.CHECKED
        datasetAccessValidationId = event.datasetAccessValidationId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        readable = event.readable
        schemaReadable = event.schemaReadable
        sampleBatchReadable = event.sampleBatchReadable
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetAccessValidationFailedEvent): AgentDatasetAccessValidationState = apply {
        datasetAccessValidationId = event.datasetAccessValidationId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetAccessRevalidatedEvent): AgentDatasetAccessValidationState = apply {
        currentState = AgentDatasetAccessValidationStateEnum.CHECKED
        datasetAccessValidationId = event.datasetAccessValidationId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        readable = event.readable
        schemaReadable = event.schemaReadable
        sampleBatchReadable = event.sampleBatchReadable
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetAccessRevalidationFailedEvent): AgentDatasetAccessValidationState = apply {
        datasetAccessValidationId = event.datasetAccessValidationId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        failureReason = event.failureReason
    }
}

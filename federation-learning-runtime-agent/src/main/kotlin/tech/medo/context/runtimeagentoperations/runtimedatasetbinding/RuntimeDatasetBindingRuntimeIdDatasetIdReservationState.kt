package tech.medo.runtimeagentoperations.runtimedatasetbinding

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent
import java.util.UUID;


@EventSourced(idType = RuntimeDatasetBindingRuntimeIdDatasetIdSelection::class)
class RuntimeDatasetBindingRuntimeIdDatasetIdReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var runtimeDatasetBindingId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: RuntimeDatasetBindingRuntimeIdDatasetIdSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(RuntimeDatasetBindingRuntimeIdDatasetIdReservationTags.RUNTIME_ID, selection.normalizedRuntimeId),
                Tag.of(RuntimeDatasetBindingRuntimeIdDatasetIdReservationTags.DATASET_ID, selection.normalizedDatasetId)
        )
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent): RuntimeDatasetBindingRuntimeIdDatasetIdReservationState = apply {
        reserved = true
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
    }
}

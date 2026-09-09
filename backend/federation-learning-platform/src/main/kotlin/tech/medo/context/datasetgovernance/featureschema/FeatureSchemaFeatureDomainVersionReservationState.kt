package tech.medo.datasetgovernance.featureschema

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.datasetgovernance.events.FeatureSchemaFeatureDomainVersionReservedEvent
import java.util.UUID;


@EventSourced(idType = FeatureSchemaFeatureDomainVersionSelection::class)
class FeatureSchemaFeatureDomainVersionReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var featureSchemaId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: FeatureSchemaFeatureDomainVersionSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(FeatureSchemaFeatureDomainVersionReservationTags.FEATURE_DOMAIN, selection.normalizedFeatureDomain),
                Tag.of(FeatureSchemaFeatureDomainVersionReservationTags.VERSION, selection.normalizedVersion)
        )
    }

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaFeatureDomainVersionReservedEvent): FeatureSchemaFeatureDomainVersionReservationState = apply {
        reserved = true
        featureSchemaId = event.featureSchemaId
    }
}

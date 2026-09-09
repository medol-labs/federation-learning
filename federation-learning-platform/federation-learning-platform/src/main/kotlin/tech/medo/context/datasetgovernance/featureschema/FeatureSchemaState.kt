package tech.medo.datasetgovernance.featureschema

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDeprecatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaRetiredEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent
import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum

import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;


@EventSourced(idType = FeatureSchemaSelection::class)
class FeatureSchemaState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: FeatureSchemaSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(FeatureSchemaTags.FEATURE_DOMAIN, selection.featureDomain.toString())),
                EventCriteria.havingTags(Tag.of(FeatureSchemaTags.VERSION, selection.version.toString()))
        )
    }


    var currentState: FeatureSchemaStateEnum? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var version: String? = null
    var dataModality: String? = null
    var features: List<FeatureDefinition> = emptyList()
    var labels: List<LabelDefinition> = emptyList()
    var featureCount: Int? = null
    var publishNote: String? = null
    var deprecationReason: String? = null
    var retirementReason: String? = null
    var supersededByFeatureSchemaId: UUID? = null
    var supersededVersion: String? = null
    var supersessionReason: String? = null
    var recommendedVersion: String? = null
    var recommendationNote: String? = null

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaDefinedEvent): FeatureSchemaState = apply {
        currentState = FeatureSchemaStateEnum.DRAFT
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        version = event.version
        dataModality = event.dataModality
        features = event.features
        labels = event.labels
        featureCount = event.featureCount
    }

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaPublishedEvent): FeatureSchemaState = apply {
        currentState = FeatureSchemaStateEnum.PUBLISHED
        featureSchemaId = event.featureSchemaId
        publishNote = event.publishNote
    }

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaDeprecatedEvent): FeatureSchemaState = apply {
        currentState = FeatureSchemaStateEnum.DEPRECATED
        featureSchemaId = event.featureSchemaId
        deprecationReason = event.deprecationReason
    }

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaRetiredEvent): FeatureSchemaState = apply {
        currentState = FeatureSchemaStateEnum.RETIRED
        featureSchemaId = event.featureSchemaId
        retirementReason = event.retirementReason
    }

    @EventSourcingHandler
    fun evolve(event: FeatureSchemaVersionSupersededEvent): FeatureSchemaState = apply {
        featureSchemaId = event.featureSchemaId
        supersededByFeatureSchemaId = event.supersededByFeatureSchemaId
        featureDomain = event.featureDomain
        supersededVersion = event.supersededVersion
        supersessionReason = event.supersessionReason
    }

    @EventSourcingHandler
    fun evolve(event: CurrentRecommendedFeatureSchemaVersionMarkedEvent): FeatureSchemaState = apply {
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        recommendedVersion = event.recommendedVersion
        recommendationNote = event.recommendationNote
    }
}

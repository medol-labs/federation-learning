package tech.medo.trainingorchestration.personalizedmodelcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum


interface PersonalizedModelCatalogReadModelProjectionUpdater {
    fun update(
        event: PersonalizedModelResultRecordedEvent,
        message: EventMessage
    )
}

open class DefaultPersonalizedModelCatalogReadModelProjectionUpdater(
    private val repository: PersonalizedModelCatalogReadModelRepository
) : PersonalizedModelCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: PersonalizedModelResultRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.personalizedModelId) ?: PersonalizedModelCatalogReadModelProjection().apply {
                this.personalizedModelId = event.personalizedModelId
        }
            entity.personalizedModelId = event.personalizedModelId
            entity.modelUpdateSubmissionId = event.modelUpdateSubmissionId
            entity.trainingJobId = event.trainingJobId
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.roundId = event.roundId
            entity.roundNumber = event.roundNumber
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.personalizationMode = event.personalizationMode
            entity.personalizationScope = event.personalizationScope
            entity.personalizedArtifactPolicy = event.personalizedArtifactPolicy
            entity.personalizationMetric = event.personalizationMetric
            entity.minimumPersonalizationLift = event.minimumPersonalizationLift
            entity.personalizationTargetType = event.personalizationTargetType
            entity.personalizationTargetId = event.personalizationTargetId
            entity.personalizationCohortId = event.personalizationCohortId
            entity.personalizationProfileId = event.personalizationProfileId
            entity.personalizedModelArtifactRef = event.personalizedModelArtifactRef
            entity.personalizedModelArtifactDigest = event.personalizedModelArtifactDigest
            entity.personalizedMetricsArtifactRef = event.personalizedMetricsArtifactRef
            entity.personalizedAccuracy = event.personalizedAccuracy
            entity.personalizationLift = event.personalizationLift
            entity.state = PersonalizedModelResultStateEnum.PersonalizedResultRecorded
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class PersonalizedModelCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(PersonalizedModelCatalogReadModelProjectionUpdater::class)
    fun defaultPersonalizedModelCatalogReadModelProjectionUpdater(
        repository: PersonalizedModelCatalogReadModelRepository
    ): PersonalizedModelCatalogReadModelProjectionUpdater =
        DefaultPersonalizedModelCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-personalized-model-catalog")
@Component
class PersonalizedModelCatalogReadModelProjector(
    private val updater: PersonalizedModelCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: PersonalizedModelResultRecordedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

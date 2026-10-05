package tech.medo.modellifecycle.modelcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.events.ModelRolledBackEvent
import tech.medo.modellifecycle.events.ModelRetiredEvent
import tech.medo.modellifecycle.domain.states.ModelStateEnum


interface ModelCatalogReadModelProjectionUpdater {
    fun update(
        event: ModelCandidateRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: ModelEvaluationPackageRecordedEvent,
        message: EventMessage
    )

    fun update(
        event: ModelApprovedEvent,
        message: EventMessage
    )

    fun update(
        event: ModelPromotedToProductionEvent,
        message: EventMessage
    )

    fun update(
        event: ModelRolledBackEvent,
        message: EventMessage
    )

    fun update(
        event: ModelRetiredEvent,
        message: EventMessage
    )
}

open class DefaultModelCatalogReadModelProjectionUpdater(
    private val repository: ModelCatalogReadModelRepository
) : ModelCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: ModelCandidateRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.trainingJobId = event.trainingJobId
            entity.finalRoundId = event.finalRoundId
            entity.modelArtifactId = event.modelArtifactId
            entity.trainingJobObjective = event.trainingJobObjective
            entity.modelArtifactDigest = event.modelArtifactDigest
            entity.evaluationReportId = event.evaluationReportId
            entity.finalGlobalAccuracy = event.finalGlobalAccuracy
            entity.state = ModelStateEnum.Candidate
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ModelEvaluationPackageRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.trainingJobId = event.trainingJobId
            entity.evaluationReportId = event.evaluationReportId
            entity.experimentId = event.experimentId
            entity.hyperparameterSnapshotId = event.hyperparameterSnapshotId
            entity.reproducibilityManifestId = event.reproducibilityManifestId
            entity.modelCardId = event.modelCardId
            entity.state = ModelStateEnum.EvaluationPackaged
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ModelApprovedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.state = ModelStateEnum.Approved
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ModelPromotedToProductionEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.releaseChannel = event.releaseChannel
            entity.productionStage = event.productionStage
            entity.state = ModelStateEnum.Production
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ModelRolledBackEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.state = ModelStateEnum.RolledBack
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: ModelRetiredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.modelId) ?: ModelCatalogReadModelProjection().apply {
                this.modelId = event.modelId
        }
            entity.modelId = event.modelId
            entity.state = ModelStateEnum.Retired
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class ModelCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(ModelCatalogReadModelProjectionUpdater::class)
    fun defaultModelCatalogReadModelProjectionUpdater(
        repository: ModelCatalogReadModelRepository
    ): ModelCatalogReadModelProjectionUpdater =
        DefaultModelCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-model-catalog")
@Component
class ModelCatalogReadModelProjector(
    private val updater: ModelCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: ModelCandidateRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ModelEvaluationPackageRecordedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ModelApprovedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ModelPromotedToProductionEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ModelRolledBackEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: ModelRetiredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

package tech.medo.runtimemonitoring.trainingalertcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface TrainingAlertCatalogReadModelProjectionUpdater {
    fun update(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingAlertAcknowledgedEvent,
        message: EventMessage
    )

    fun update(
        event: TrainingAlertResolvedEvent,
        message: EventMessage
    )
}

open class DefaultTrainingAlertCatalogReadModelProjectionUpdater(
    private val repository: TrainingAlertCatalogReadModelRepository
) : TrainingAlertCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.nodeId = event.nodeId
            entity.trainingJobId = event.trainingJobId
            entity.runtimeNodeName = event.runtimeNodeName
            entity.trainingJobObjective = event.trainingJobObjective
            entity.severity = event.severity
            entity.message = event.message
            entity.state = TrainingAlertStateEnum.Raised
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: TrainingAlertAcknowledgedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.state = TrainingAlertStateEnum.Acknowledged
            entity.acknowledgedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: TrainingAlertResolvedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.resolutionSummary = event.resolutionSummary
            entity.state = TrainingAlertStateEnum.Resolved
            entity.resolvedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class TrainingAlertCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(TrainingAlertCatalogReadModelProjectionUpdater::class)
    fun defaultTrainingAlertCatalogReadModelProjectionUpdater(
        repository: TrainingAlertCatalogReadModelRepository
    ): TrainingAlertCatalogReadModelProjectionUpdater =
        DefaultTrainingAlertCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-training-alert-catalog")
@Component
class TrainingAlertCatalogReadModelProjector(
    private val updater: TrainingAlertCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: TrainingAlertAcknowledgedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: TrainingAlertResolvedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

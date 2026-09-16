package tech.medo.runtimemonitoring.trainingalertcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface TrainingAlertCatalogReadModelProjectionUpdater {
    fun update(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    )

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

@Component
@ConditionalOnMissingBean(TrainingAlertCatalogReadModelProjectionUpdater::class)
class DefaultTrainingAlertCatalogReadModelProjectionUpdater(
    private val repository: TrainingAlertCatalogReadModelRepository
) : TrainingAlertCatalogReadModelProjectionUpdater {
    override fun update(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate TrainingAlertCatalogReadModelProjection.
    }

    override fun update(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeNodeInventoryReportedEvent does not provide enough key fields to locate TrainingAlertCatalogReadModelProjection.
    }

    @Transactional
    override fun update(
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
            entity.state = TrainingAlertStateEnum.RAISED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: TrainingAlertAcknowledgedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.state = TrainingAlertStateEnum.ACKNOWLEDGED
            entity.acknowledgedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: TrainingAlertResolvedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.resolutionSummary = event.resolutionSummary
            entity.state = TrainingAlertStateEnum.RESOLVED
            entity.resolvedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Namespace("readmodel-training-alert-catalog")
@Component
class TrainingAlertCatalogReadModelProjector(
    private val updater: TrainingAlertCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: TrainingJobCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeNodeInventoryReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

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

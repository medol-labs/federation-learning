package tech.medo.runtimemonitoring.trainingalertcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.runtimemonitoring.events.RuntimeNodeInventoryReportedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class TrainingAlertCatalogReadModelProjector(private val repository: TrainingAlertCatalogReadModelRepository) {
    @EventHandler
    fun on(event: TrainingJobCreatedEvent) {
        // Skipped: TrainingJobCreatedEvent does not provide enough key fields to locate TrainingAlertCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeNodeInventoryReportedEvent) {
        // Skipped: RuntimeNodeInventoryReportedEvent does not provide enough key fields to locate TrainingAlertCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: TrainingAlertRaisedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.alertId) ?: TrainingAlertCatalogReadModelProjection().apply {
                this.alertId = event.alertId
        }
            entity.alertId = event.alertId
            entity.nodeId = event.nodeId
            entity.trainingJobId = event.trainingJobId
            entity.severity = event.severity
            entity.message = event.message
            entity.state = TrainingAlertStateEnum.RAISED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
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

    @EventHandler
    fun on(
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

package tech.medo.infrastructure.secondary.runtimegovernance.runtimeidentitycatalog

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelProjection
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelProjectionUpdater
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import tech.medo.runtimegovernance.runtimeidentitycatalog.toReadModel
import tech.medo.shared.application.metadata.ProjectionMetadata
import tech.medo.shared.application.sync.SyncOutboxAppender

@Component
class RuntimeIdentityCatalogReadModelOutboxProjectionUpdater(
    private val repository: RuntimeIdentityCatalogReadModelRepository,
    private val outbox: SyncOutboxAppender
) : RuntimeIdentityCatalogReadModelProjectionUpdater {
    override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // RuntimeIdentityCatalog cannot be located from OrganizationRegisteredEvent alone.
    }

    @Transactional
    override fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
            runtimeId = event.runtimeId
        }
        entity.runtimeId = event.runtimeId
        entity.runtimeInfrastructureId = event.runtimeInfrastructureId
        entity.runtimeAgentId = event.runtimeAgentId
        entity.organizationId = event.organizationId
        entity.organizationName = event.organizationName
        entity.runtimeName = event.runtimeName
        entity.identityStatus = "Active"
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.runtimeId.toString(), message)
    }

    @Transactional
    override fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
            runtimeId = event.runtimeId
        }
        entity.runtimeId = event.runtimeId
        entity.identityStatus = "Revoked"
        entity.revokedAt = eventTime(message)
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.runtimeId.toString(), message)
    }

    private fun saveAndPublish(
        entity: RuntimeIdentityCatalogReadModelProjection,
        readModelKey: String,
        message: EventMessage
    ) {
        repository.save(entity)
        outbox.appendReadModel(
            sourceContext = "RuntimeProvisioning",
            sourceReadModel = "RuntimeIdentityCatalog",
            readModelKey = readModelKey,
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)
}

package tech.medo.infrastructure.secondary.organizationmanagement.organizationdirectory

import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum
import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelProjection
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelProjectionUpdater
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelRepository
import tech.medo.organizationmanagement.organizationdirectory.toReadModel
import tech.medo.shared.application.metadata.ProjectionMetadata
import tech.medo.shared.application.sync.SyncReadModelOutboxAppender

@Component
class OrganizationDirectoryReadModelOutboxProjectionUpdater(
    private val repository: OrganizationDirectoryReadModelRepository,
    private val outbox: SyncReadModelOutboxAppender
) : OrganizationDirectoryReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
            organizationId = event.organizationId
        }
        entity.organizationId = event.organizationId
        entity.organizationName = event.organizationName
        entity.organizationType = event.organizationType
        entity.state = OrganizationStateEnum.REGISTERED
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.organizationId.toString(), message)
    }

    @Transactional
    override fun update(
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
            organizationId = event.organizationId
        }
        entity.organizationId = event.organizationId
        entity.state = OrganizationStateEnum.ACTIVE
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.organizationId.toString(), message)
    }

    @Transactional
    override fun update(
        event: OrganizationDeactivatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
            organizationId = event.organizationId
        }
        entity.organizationId = event.organizationId
        entity.state = OrganizationStateEnum.DEACTIVATED
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.organizationId.toString(), message)
    }

    @Transactional
    override fun update(
        event: OrganizationReactivatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
            organizationId = event.organizationId
        }
        entity.organizationId = event.organizationId
        entity.state = OrganizationStateEnum.ACTIVE
        ProjectionMetadata.assign(entity, message)
        saveAndPublish(entity, event.organizationId.toString(), message)
    }

    private fun saveAndPublish(
        entity: OrganizationDirectoryReadModelProjection,
        readModelKey: String,
        message: EventMessage
    ) {
        repository.save(entity)
        outbox.append(
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            readModelKey = readModelKey,
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }
}

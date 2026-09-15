package tech.medo.organizationmanagement.organizationdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


@Namespace("readmodel-organization-directory")
@Component
class OrganizationDirectoryReadModelProjector(private val repository: OrganizationDirectoryReadModelRepository) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
                this.organizationId = event.organizationId
        }
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.organizationType = event.organizationType
            entity.state = OrganizationStateEnum.REGISTERED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
                this.organizationId = event.organizationId
        }
            entity.organizationId = event.organizationId
            entity.state = OrganizationStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: OrganizationDeactivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
                this.organizationId = event.organizationId
        }
            entity.organizationId = event.organizationId
            entity.state = OrganizationStateEnum.DEACTIVATED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: OrganizationReactivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.organizationId) ?: OrganizationDirectoryReadModelProjection().apply {
                this.organizationId = event.organizationId
        }
            entity.organizationId = event.organizationId
            entity.state = OrganizationStateEnum.ACTIVE
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

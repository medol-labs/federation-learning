package tech.medo.organizationmanagement.organizationdirectory

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.shared.application.sync.SyncOutboxAppender

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.OrganizationActivatedEvent
import tech.medo.organizationmanagement.events.OrganizationDeactivatedEvent
import tech.medo.organizationmanagement.events.OrganizationReactivatedEvent
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum


interface OrganizationDirectoryReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: OrganizationActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: OrganizationDeactivatedEvent,
        message: EventMessage
    )

    fun update(
        event: OrganizationReactivatedEvent,
        message: EventMessage
    )
}

open class DefaultOrganizationDirectoryReadModelProjectionUpdater(
    private val repository: OrganizationDirectoryReadModelRepository,
    private val outbox: SyncOutboxAppender
) : OrganizationDirectoryReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

        outbox.appendReadModel(
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            readModelKey = event.organizationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    open override fun update(
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

        outbox.appendReadModel(
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            readModelKey = event.organizationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    open override fun update(
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

        outbox.appendReadModel(
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            readModelKey = event.organizationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    open override fun update(
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

        outbox.appendReadModel(
            sourceContext = "OrganizationManagement",
            sourceReadModel = "OrganizationDirectory",
            readModelKey = event.organizationId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

}

@Configuration(proxyBeanMethods = false)
class OrganizationDirectoryReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(OrganizationDirectoryReadModelProjectionUpdater::class)
    fun defaultOrganizationDirectoryReadModelProjectionUpdater(
        repository: OrganizationDirectoryReadModelRepository,
        outbox: SyncOutboxAppender
    ): OrganizationDirectoryReadModelProjectionUpdater =
        DefaultOrganizationDirectoryReadModelProjectionUpdater(repository, outbox)
}

@Namespace("readmodel-organization-directory")
@Component
class OrganizationDirectoryReadModelProjector(
    private val updater: OrganizationDirectoryReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: OrganizationActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: OrganizationDeactivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: OrganizationReactivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

package tech.medo.runtimegovernance.runtimeidentitycatalog

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
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeIdentityCatalogReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeIdentityCatalogReadModelProjectionUpdater(
    private val repository: RuntimeIdentityCatalogReadModelRepository,
    private val outbox: SyncOutboxAppender
) : RuntimeIdentityCatalogReadModelProjectionUpdater {
    open override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeIdentityCatalogReadModelProjection.
    }

    @Transactional
    open override fun update(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
                this.runtimeId = event.runtimeId
        }
            entity.runtimeId = event.runtimeId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.identityStatus = "Active"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

        outbox.appendReadModel(
            sourceContext = "RuntimeProvisioning",
            sourceReadModel = "RuntimeIdentityCatalog",
            readModelKey = event.runtimeId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    @Transactional
    open override fun update(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeId) ?: RuntimeIdentityCatalogReadModelProjection().apply {
                this.runtimeId = event.runtimeId
        }
            entity.runtimeId = event.runtimeId
            entity.identityStatus = "Revoked"
            entity.revokedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

        outbox.appendReadModel(
            sourceContext = "RuntimeProvisioning",
            sourceReadModel = "RuntimeIdentityCatalog",
            readModelKey = event.runtimeId.toString(),
            operation = "UPSERT",
            payload = entity.toReadModel(),
            message = message
        )
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class RuntimeIdentityCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeIdentityCatalogReadModelProjectionUpdater::class)
    fun defaultRuntimeIdentityCatalogReadModelProjectionUpdater(
        repository: RuntimeIdentityCatalogReadModelRepository,
        outbox: SyncOutboxAppender
    ): RuntimeIdentityCatalogReadModelProjectionUpdater =
        DefaultRuntimeIdentityCatalogReadModelProjectionUpdater(repository, outbox)
}

@Namespace("readmodel-runtime-identity-catalog")
@Component
class RuntimeIdentityCatalogReadModelProjector(
    private val updater: RuntimeIdentityCatalogReadModelProjectionUpdater
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
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityRevokedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

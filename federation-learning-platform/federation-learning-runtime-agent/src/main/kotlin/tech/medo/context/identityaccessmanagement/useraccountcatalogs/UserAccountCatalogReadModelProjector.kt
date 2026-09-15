package tech.medo.identityaccessmanagement.useraccountcatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.events.UserAccountDeactivatedEvent
import tech.medo.identityaccessmanagement.events.UserAccountLoginPasswordGeneratedEvent



@Namespace("readmodel-user-account-catalog")
@Component
class UserAccountCatalogReadModelProjector(private val repository: UserAccountCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: UserAccountRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.userAccountId) ?: UserAccountCatalogReadModelProjection().apply {
                this.userAccountId = event.userAccountId
        }
            entity.userAccountId = event.userAccountId
            entity.username = event.username
            entity.providerSubject = event.providerSubject
            entity.userSource = event.userSource
            entity.passwordHash = event.passwordHash
            entity.active = true
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: UserAccountDeactivatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.userAccountId) ?: UserAccountCatalogReadModelProjection().apply {
                this.userAccountId = event.userAccountId
        }
            entity.userAccountId = event.userAccountId
            entity.active = false
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: UserAccountLoginPasswordGeneratedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.userAccountId) ?: UserAccountCatalogReadModelProjection().apply {
                this.userAccountId = event.userAccountId
        }
            entity.userAccountId = event.userAccountId
            entity.passwordHash = event.passwordHash
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

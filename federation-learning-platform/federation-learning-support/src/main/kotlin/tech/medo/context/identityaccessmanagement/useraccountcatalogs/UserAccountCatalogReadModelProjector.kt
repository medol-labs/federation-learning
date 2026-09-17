package tech.medo.identityaccessmanagement.useraccountcatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.events.UserAccountDeactivatedEvent
import tech.medo.identityaccessmanagement.events.UserAccountLoginPasswordGeneratedEvent



interface UserAccountCatalogReadModelProjectionUpdater {
    fun update(
        event: UserAccountRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: UserAccountDeactivatedEvent,
        message: EventMessage
    )

    fun update(
        event: UserAccountLoginPasswordGeneratedEvent,
        message: EventMessage
    )
}

open class DefaultUserAccountCatalogReadModelProjectionUpdater(
    private val repository: UserAccountCatalogReadModelRepository
) : UserAccountCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class UserAccountCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserAccountCatalogReadModelProjectionUpdater::class)
    fun defaultUserAccountCatalogReadModelProjectionUpdater(
        repository: UserAccountCatalogReadModelRepository
    ): UserAccountCatalogReadModelProjectionUpdater =
        DefaultUserAccountCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-user-account-catalog")
@Component
class UserAccountCatalogReadModelProjector(
    private val updater: UserAccountCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: UserAccountRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: UserAccountDeactivatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: UserAccountLoginPasswordGeneratedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

package tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.identityaccessmanagement.events.ServiceAccountApiTokenIssuedEvent



interface ServiceAccountApiTokenCatalogReadModelProjectionUpdater {
    fun update(
        event: ServiceAccountApiTokenIssuedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(ServiceAccountApiTokenCatalogReadModelProjectionUpdater::class)
class DefaultServiceAccountApiTokenCatalogReadModelProjectionUpdater(
    private val repository: ServiceAccountApiTokenCatalogReadModelRepository
) : ServiceAccountApiTokenCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: ServiceAccountApiTokenIssuedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.apiTokenId) ?: ServiceAccountApiTokenCatalogReadModelProjection().apply {
                this.apiTokenId = event.apiTokenId
        }
            entity.apiTokenId = event.apiTokenId
            entity.userAccountId = event.userAccountId
            entity.username = event.username
            entity.tokenName = event.tokenName
            entity.tokenPrefix = event.tokenPrefix
            entity.issuedAt = event.issuedAt
            entity.roles = event.roles
            entity.permissions = event.permissions
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Namespace("readmodel-service-account-api-token-catalog")
@Component
class ServiceAccountApiTokenCatalogReadModelProjector(
    private val updater: ServiceAccountApiTokenCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: ServiceAccountApiTokenIssuedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}

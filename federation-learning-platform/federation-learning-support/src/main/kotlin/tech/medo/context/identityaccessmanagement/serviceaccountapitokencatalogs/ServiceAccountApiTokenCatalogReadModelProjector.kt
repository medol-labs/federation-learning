package tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.identityaccessmanagement.events.ServiceAccountApiTokenIssuedEvent



@Namespace("readmodel-service-account-api-token-catalog")
@Component
class ServiceAccountApiTokenCatalogReadModelProjector(private val repository: ServiceAccountApiTokenCatalogReadModelRepository) {
    @EventHandler
    fun on(
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

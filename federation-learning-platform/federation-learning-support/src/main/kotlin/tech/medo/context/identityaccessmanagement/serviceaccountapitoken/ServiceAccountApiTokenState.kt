package tech.medo.identityaccessmanagement.serviceaccountapitoken

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.identityaccessmanagement.events.ServiceAccountApiTokenIssuedEvent
import tech.medo.identityaccessmanagement.domain.states.ServiceAccountApiTokenStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = ServiceAccountApiTokenTags.USER_ACCOUNT_ID)
class ServiceAccountApiTokenState @EntityCreator constructor() {

    var currentState: ServiceAccountApiTokenStateEnum? = null
    var apiTokenId: UUID? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var tokenName: String? = null
    var tokenPrefix: String? = null
    var tokenDigest: String? = null
    var issuedAt: String? = null
    var roles: List<String> = emptyList()
    var permissions: List<String> = emptyList()

    @EventSourcingHandler
    fun evolve(event: ServiceAccountApiTokenIssuedEvent): ServiceAccountApiTokenState = apply {
        currentState = ServiceAccountApiTokenStateEnum.ISSUED
        apiTokenId = event.apiTokenId
        userAccountId = event.userAccountId
        username = event.username
        tokenName = event.tokenName
        tokenPrefix = event.tokenPrefix
        tokenDigest = event.tokenDigest
        issuedAt = event.issuedAt
        roles = event.roles
        permissions = event.permissions
    }
}

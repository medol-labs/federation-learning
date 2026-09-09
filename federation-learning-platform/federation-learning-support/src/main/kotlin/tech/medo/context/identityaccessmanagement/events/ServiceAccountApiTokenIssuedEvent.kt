package tech.medo.identityaccessmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ServiceAccountApiTokenIssuedEvent(
    val apiTokenId: UUID,
    @EventTag(key = "userAccountId")
    val userAccountId: UUID,
    val username: String,
    val tokenName: String,
    val tokenPrefix: String,
    val tokenDigest: String,
    val issuedAt: String,
    val roles: List<String>,
    val permissions: List<String>
)

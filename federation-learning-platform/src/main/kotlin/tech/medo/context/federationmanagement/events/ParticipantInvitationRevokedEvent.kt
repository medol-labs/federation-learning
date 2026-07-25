package tech.medo.federationmanagement.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class ParticipantInvitationRevokedEvent(
    @EventTag(key = "federationId")
    val federationId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val revokeReason: String
)

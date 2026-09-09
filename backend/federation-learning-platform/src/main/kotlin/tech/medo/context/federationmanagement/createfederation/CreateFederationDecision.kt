package tech.medo.federationmanagement.createfederation

import tech.medo.federationmanagement.createfederation.CreateFederationCommand


import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.federationmanagement.events.FederationNameReservedEvent
import tech.medo.federationmanagement.federation.FederationState

import tech.medo.federationmanagement.federation.FederationNameReservationState



interface CreateFederationDecision {
    fun decide(command: CreateFederationCommand, federationNameReservation: FederationNameReservationState): List<Any> {
        require(!federationNameReservation.reserved) {
            "Name already exists."
        }
        return listOf(
                        FederationNameReservedEvent(federationId = command.federationId, federationName = command.federationName, normalizedName = command.federationName.trim().lowercase()),
            FederationCreatedEvent(federationId = command.federationId, federationName = command.federationName, description = command.description, minimumParticipantCount = command.minimumParticipantCount)
        )
    }
}

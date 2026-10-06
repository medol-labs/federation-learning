package tech.medo.federationmanagement.removeparticipant

import tech.medo.federationmanagement.removeparticipant.RemoveParticipantCommand


import tech.medo.federationmanagement.events.ParticipantRemovedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface RemoveParticipantDecision {
    fun decide(command: RemoveParticipantCommand, state: FederationMembershipState): List<Any> {
        if (state.currentState != FederationMembershipStateEnum.Suspended) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.removeParticipant.requiresState",
                args = mapOf(
                    "command" to "RemoveParticipant",
                    "aggregate" to "FederationMembership",
                    "expectedState" to "Suspended",
                    "actualState" to state.currentState.toString()
                ),
                message = "RemoveParticipant requires FederationMembership to be Suspended."
            )
        }
        return listOf(
            ParticipantRemovedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, removalReason = command.removalReason)
        )
    }
}

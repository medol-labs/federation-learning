package tech.medo.federationmanagement.rejectparticipant

import tech.medo.federationmanagement.rejectparticipant.RejectParticipantCommand


import tech.medo.federationmanagement.events.ParticipantRejectedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface RejectParticipantDecision {
    fun decide(command: RejectParticipantCommand, state: FederationMembershipState): List<Any> {
        if (state.currentState != FederationMembershipStateEnum.Invited) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.rejectParticipant.requiresState",
                args = mapOf(
                    "command" to "RejectParticipant",
                    "aggregate" to "FederationMembership",
                    "expectedState" to "Invited",
                    "actualState" to state.currentState.toString()
                ),
                message = "RejectParticipant requires FederationMembership to be Invited."
            )
        }
        return listOf(
            ParticipantRejectedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, rejectionReason = command.rejectionReason)
        )
    }
}

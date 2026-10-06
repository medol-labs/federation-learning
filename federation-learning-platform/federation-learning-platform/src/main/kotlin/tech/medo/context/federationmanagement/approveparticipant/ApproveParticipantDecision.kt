package tech.medo.federationmanagement.approveparticipant

import tech.medo.federationmanagement.approveparticipant.ApproveParticipantCommand


import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface ApproveParticipantDecision {
    fun decide(command: ApproveParticipantCommand, state: FederationMembershipState): List<Any> {
        if (state.currentState != FederationMembershipStateEnum.Invited) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.approveParticipant.requiresState",
                args = mapOf(
                    "command" to "ApproveParticipant",
                    "aggregate" to "FederationMembership",
                    "expectedState" to "Invited",
                    "actualState" to state.currentState.toString()
                ),
                message = "ApproveParticipant requires FederationMembership to be Invited."
            )
        }
        return listOf(
            ParticipantJoinedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, approvalNote = command.approvalNote)
        )
    }
}

package tech.medo.federationmanagement.suspendparticipant

import tech.medo.federationmanagement.suspendparticipant.SuspendParticipantCommand


import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface SuspendParticipantDecision {
    fun decide(command: SuspendParticipantCommand, state: FederationMembershipState): List<Any> {
        if (state.currentState != FederationMembershipStateEnum.Active) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.suspendParticipant.requiresState",
                args = mapOf(
                    "command" to "SuspendParticipant",
                    "aggregate" to "FederationMembership",
                    "expectedState" to "Active",
                    "actualState" to state.currentState.toString()
                ),
                message = "SuspendParticipant requires FederationMembership to be Active."
            )
        }
        return listOf(
            ParticipantSuspendedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, suspensionReason = command.suspensionReason)
        )
    }
}

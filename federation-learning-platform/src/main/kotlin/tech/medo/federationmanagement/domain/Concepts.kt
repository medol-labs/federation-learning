package tech.medo.federationmanagement.domain

object Concepts {
    data object Federation {
        const val NAME = "Federation"
        val slices = listOf("CreateFederation", "ActivateFederation", "SuspendFederation", "ReactivateFederation", "FederationOverview")
        val states = listOf("Draft", "Active", "Suspended")
    }

    data object FederationMembership {
        const val NAME = "FederationMembership"
        val slices = listOf("InviteParticipant", "ApproveParticipant", "RejectParticipant", "RevokeParticipantInvitation", "SuspendParticipant", "RemoveParticipant", "FederationMembershipDirectory")
        val states = listOf("Invited", "Active", "Rejected", "InvitationRevoked", "Suspended", "Removed")
    }
}

package tech.medo.federationmanagement.revokeparticipantinvitation

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory


import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/federationmembership")
class RevokeParticipantInvitationResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('revoke_participant_invitation:execute')")
    @PostMapping("/revokeparticipantinvitation")
    fun RevokeParticipantInvitation(
        @Valid @RequestBody command: RevokeParticipantInvitationCommand,
        request: HttpServletRequest
    ): CompletableFuture<RevokeParticipantInvitationCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

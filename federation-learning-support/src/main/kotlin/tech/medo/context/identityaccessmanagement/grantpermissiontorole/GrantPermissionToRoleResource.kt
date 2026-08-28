package tech.medo.identityaccessmanagement.grantpermissiontorole

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
@RequestMapping("/role")
class GrantPermissionToRoleResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('grant_permission_to_role:execute')")
    @PostMapping("/grantpermissiontorole")
    fun GrantPermissionToRole(
        @Valid @RequestBody command: GrantPermissionToRoleCommand,
        request: HttpServletRequest
    ): CompletableFuture<GrantPermissionToRoleCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

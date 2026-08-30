package tech.medo.organizationmanagement.binduseraccounttoorganization

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
@RequestMapping("/userorganizationmembership")
class BindUserAccountToOrganizationResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('bind_user_account_to_organization:execute')")
    @PostMapping("/binduseraccounttoorganization")
    fun BindUserAccountToOrganization(
        @Valid @RequestBody command: BindUserAccountToOrganizationCommand,
        request: HttpServletRequest
    ): CompletableFuture<BindUserAccountToOrganizationCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

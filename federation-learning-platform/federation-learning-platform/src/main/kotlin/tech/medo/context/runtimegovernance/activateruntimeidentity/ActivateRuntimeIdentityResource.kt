package tech.medo.runtimegovernance.activateruntimeidentity

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
@RequestMapping("/runtimeidentity")
class ActivateRuntimeIdentityResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('activate_runtime_identity:execute')")
    @PostMapping("/activateruntimeidentity")
    fun ActivateRuntimeIdentity(
        @Valid @RequestBody command: ActivateRuntimeIdentityCommand,
        request: HttpServletRequest
    ): CompletableFuture<ActivateRuntimeIdentityCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

package tech.medo.trainingorchestration.registerruntimeengineprofile

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
@RequestMapping("/runtimeengineprofile")
class RegisterRuntimeEngineProfileResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('register_runtime_engine_profile:execute')")
    @PostMapping("/registerruntimeengineprofile")
    fun RegisterRuntimeEngineProfile(
        @Valid @RequestBody command: RegisterRuntimeEngineProfileCommand,
        request: HttpServletRequest
    ): CompletableFuture<RegisterRuntimeEngineProfileCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

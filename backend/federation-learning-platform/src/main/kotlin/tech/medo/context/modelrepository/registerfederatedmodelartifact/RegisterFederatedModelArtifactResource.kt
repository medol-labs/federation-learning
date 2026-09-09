package tech.medo.modelrepository.registerfederatedmodelartifact

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
@RequestMapping("/modelartifact")
class RegisterFederatedModelArtifactResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('register_federated_model_artifact:execute')")
    @PostMapping("/registerfederatedmodelartifact")
    fun RegisterFederatedModelArtifact(
        @Valid @RequestBody command: RegisterFederatedModelArtifactCommand,
        request: HttpServletRequest
    ): CompletableFuture<RegisterFederatedModelArtifactCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

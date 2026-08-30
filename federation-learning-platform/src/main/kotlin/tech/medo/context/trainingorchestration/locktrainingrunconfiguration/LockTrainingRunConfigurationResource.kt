package tech.medo.trainingorchestration.locktrainingrunconfiguration

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
@RequestMapping("/trainingrunconfiguration")
class LockTrainingRunConfigurationResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('lock_training_run_configuration:execute')")
    @PostMapping("/locktrainingrunconfiguration")
    fun LockTrainingRunConfiguration(
        @Valid @RequestBody command: LockTrainingRunConfigurationCommand,
        request: HttpServletRequest
    ): CompletableFuture<LockTrainingRunConfigurationCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

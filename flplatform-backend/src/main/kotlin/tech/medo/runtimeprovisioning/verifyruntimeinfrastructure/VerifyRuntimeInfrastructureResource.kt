package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory
import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/runtimeinfrastructure")
class VerifyRuntimeInfrastructureResource(private val commandGateway: CommandGateway) {
    @PostMapping("/verifyruntimeinfrastructure")
    fun VerifyRuntimeInfrastructure(
        @Valid @RequestBody command: VerifyRuntimeInfrastructureCommand,
        request: HttpServletRequest
    ): CompletableFuture<VerifyRuntimeInfrastructureCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

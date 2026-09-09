package tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared

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
@RequestMapping("/runtimeinfrastructure")
class ConfirmRuntimeInfrastructurePreparedResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('confirm_runtime_infrastructure_prepared:execute')")
    @PostMapping("/confirmruntimeinfrastructureprepared")
    fun ConfirmRuntimeInfrastructurePrepared(
        @Valid @RequestBody command: ConfirmRuntimeInfrastructurePreparedCommand,
        request: HttpServletRequest
    ): CompletableFuture<ConfirmRuntimeInfrastructurePreparedCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

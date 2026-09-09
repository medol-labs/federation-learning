package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

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
@RequestMapping("/runtimedatasetbinding")
class ConfigureRuntimeDatasetBindingResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('configure_runtime_dataset_binding:execute')")
    @PostMapping("/configureruntimedatasetbinding")
    fun ConfigureRuntimeDatasetBinding(
        @Valid @RequestBody command: ConfigureRuntimeDatasetBindingCommand,
        request: HttpServletRequest
    ): CompletableFuture<ConfigureRuntimeDatasetBindingCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

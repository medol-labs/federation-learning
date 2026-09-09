package tech.medo.runtimeagentoperations.observeruntimeenginejob

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
@RequestMapping("/roundexecution")
class ObserveRuntimeEngineJobResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('observe_runtime_engine_job:execute')")
    @PostMapping("/observeruntimeenginejob")
    fun ObserveRuntimeEngineJob(
        @Valid @RequestBody command: ObserveRuntimeEngineJobCommand,
        request: HttpServletRequest
    ): CompletableFuture<ObserveRuntimeEngineJobCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

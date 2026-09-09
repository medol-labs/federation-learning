package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

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
class ReleaseRuntimeEngineJobAfterFailureResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('release_runtime_engine_job_after_failure:execute')")
    @PostMapping("/releaseruntimeenginejobafterfailure")
    fun ReleaseRuntimeEngineJobAfterFailure(
        @Valid @RequestBody command: ReleaseRuntimeEngineJobAfterFailureCommand,
        request: HttpServletRequest
    ): CompletableFuture<ReleaseRuntimeEngineJobAfterFailureCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

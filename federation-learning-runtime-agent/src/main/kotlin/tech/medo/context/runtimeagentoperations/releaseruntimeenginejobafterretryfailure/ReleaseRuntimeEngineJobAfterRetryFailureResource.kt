package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

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
class ReleaseRuntimeEngineJobAfterRetryFailureResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('release_runtime_engine_job_after_retry_failure:execute')")
    @PostMapping("/releaseruntimeenginejobafterretryfailure")
    fun ReleaseRuntimeEngineJobAfterRetryFailure(
        @Valid @RequestBody command: ReleaseRuntimeEngineJobAfterRetryFailureCommand,
        request: HttpServletRequest
    ): CompletableFuture<ReleaseRuntimeEngineJobAfterRetryFailureCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

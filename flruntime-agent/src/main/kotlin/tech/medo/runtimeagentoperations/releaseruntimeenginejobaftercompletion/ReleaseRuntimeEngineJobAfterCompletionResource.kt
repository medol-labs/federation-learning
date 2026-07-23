package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

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
@RequestMapping("/roundexecution")
class ReleaseRuntimeEngineJobAfterCompletionResource(private val commandGateway: CommandGateway) {
    @PostMapping("/releaseruntimeenginejobaftercompletion")
    fun ReleaseRuntimeEngineJobAfterCompletion(
        @Valid @RequestBody command: ReleaseRuntimeEngineJobAfterCompletionCommand,
        request: HttpServletRequest
    ): CompletableFuture<ReleaseRuntimeEngineJobAfterCompletionCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

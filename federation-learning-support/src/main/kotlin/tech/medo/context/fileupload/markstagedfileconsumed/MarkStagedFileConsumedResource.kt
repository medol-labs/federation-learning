package tech.medo.fileupload.markstagedfileconsumed

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
@RequestMapping("/stagedfile")
class MarkStagedFileConsumedResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('mark_staged_file_consumed:execute')")
    @PostMapping("/markstagedfileconsumed")
    fun MarkStagedFileConsumed(
        @Valid @RequestBody command: MarkStagedFileConsumedCommand,
        request: HttpServletRequest
    ): CompletableFuture<MarkStagedFileConsumedCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

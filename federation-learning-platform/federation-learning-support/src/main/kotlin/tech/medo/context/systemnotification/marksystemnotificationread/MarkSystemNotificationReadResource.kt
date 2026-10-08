package tech.medo.systemnotification.marksystemnotificationread

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
@RequestMapping("/systemnotification")
class MarkSystemNotificationReadResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('mark_system_notification_read:execute')")
    @PostMapping("/marksystemnotificationread")
    fun MarkSystemNotificationRead(
        @Valid @RequestBody command: MarkSystemNotificationReadCommand,
        request: HttpServletRequest
    ): CompletableFuture<MarkSystemNotificationReadCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

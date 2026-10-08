package tech.medo.systemnotification.publishsystemnotification

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
class PublishSystemNotificationResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('publish_system_notification:execute')")
    @PostMapping("/publishsystemnotification")
    fun PublishSystemNotification(
        @Valid @RequestBody command: PublishSystemNotificationCommand,
        request: HttpServletRequest
    ): CompletableFuture<PublishSystemNotificationCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

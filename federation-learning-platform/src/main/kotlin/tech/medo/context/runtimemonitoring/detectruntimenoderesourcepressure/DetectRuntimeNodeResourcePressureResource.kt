package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

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
@RequestMapping("/runtimenoderesourcepressure")
class DetectRuntimeNodeResourcePressureResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('detect_runtime_node_resource_pressure:execute')")
    @PostMapping("/detectruntimenoderesourcepressure")
    fun DetectRuntimeNodeResourcePressure(
        @Valid @RequestBody command: DetectRuntimeNodeResourcePressureCommand,
        request: HttpServletRequest
    ): CompletableFuture<DetectRuntimeNodeResourcePressureCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

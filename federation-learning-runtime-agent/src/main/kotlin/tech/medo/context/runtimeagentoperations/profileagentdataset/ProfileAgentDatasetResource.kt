package tech.medo.runtimeagentoperations.profileagentdataset

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
@RequestMapping("/agentdatasetprofile")
class ProfileAgentDatasetResource(private val commandGateway: CommandGateway) {
    @PostMapping("/profileagentdataset")
    fun ProfileAgentDataset(
        @Valid @RequestBody command: ProfileAgentDatasetCommand,
        request: HttpServletRequest
    ): CompletableFuture<ProfileAgentDatasetCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

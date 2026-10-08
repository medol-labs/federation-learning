package tech.medo.trainingorchestration.recordpersonalizedmodelresult

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
@RequestMapping("/personalizedmodelresult")
class RecordPersonalizedModelResultResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('record_personalized_model_result:execute')")
    @PostMapping("/recordpersonalizedmodelresult")
    fun RecordPersonalizedModelResult(
        @Valid @RequestBody command: RecordPersonalizedModelResultCommand,
        request: HttpServletRequest
    ): CompletableFuture<RecordPersonalizedModelResultCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

package tech.medo.runtimeagentoperations.rejectdatasetfortraining

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
@RequestMapping("/dataset")
class RejectDatasetForTrainingResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('reject_dataset_for_training:execute')")
    @PostMapping("/rejectdatasetfortraining")
    fun RejectDatasetForTraining(
        @Valid @RequestBody command: RejectDatasetForTrainingCommand,
        request: HttpServletRequest
    ): CompletableFuture<RejectDatasetForTrainingCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

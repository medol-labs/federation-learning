package tech.medo.runtimeagentoperations.approvedatasetfortraining

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
class ApproveDatasetForTrainingResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('approve_dataset_for_training:execute')")
    @PostMapping("/approvedatasetfortraining")
    fun ApproveDatasetForTraining(
        @Valid @RequestBody command: ApproveDatasetForTrainingCommand,
        request: HttpServletRequest
    ): CompletableFuture<ApproveDatasetForTrainingCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

package tech.medo.trainingorchestration.generateparticipantexecutionplan

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
@RequestMapping("/participantexecutionplan")
class GenerateParticipantExecutionPlanResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('generate_participant_execution_plan:execute')")
    @PostMapping("/generateparticipantexecutionplan")
    fun GenerateParticipantExecutionPlan(
        @Valid @RequestBody command: GenerateParticipantExecutionPlanCommand,
        request: HttpServletRequest
    ): CompletableFuture<GenerateParticipantExecutionPlanCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}

package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class VerifyRuntimeInfrastructureCommandHandler(
    private val decision: VerifyRuntimeInfrastructureDecision,
    private val verifyRuntimeInfrastructureService: VerifyRuntimeInfrastructureService
) {
    @CommandHandler
    fun handle(
        command: VerifyRuntimeInfrastructureCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        val input = RuntimeInfrastructureVerificationInput(runtimeInfrastructureId = command.runtimeInfrastructureId, agentInstallMode = command.agentInstallMode, observedNodeCount = command.observedNodeCount)
        val portResult = verifyRuntimeInfrastructureService.verify(input)
        val now = java.time.LocalDateTime.now()
        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}

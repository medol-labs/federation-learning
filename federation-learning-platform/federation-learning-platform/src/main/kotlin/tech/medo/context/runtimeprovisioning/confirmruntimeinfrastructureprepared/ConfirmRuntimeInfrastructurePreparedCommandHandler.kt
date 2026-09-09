package tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared.ConfirmRuntimeInfrastructurePreparedCommand

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class ConfirmRuntimeInfrastructurePreparedCommandHandler(
    private val decision: ConfirmRuntimeInfrastructurePreparedDecision
) {
    @CommandHandler
    fun handle(
        command: ConfirmRuntimeInfrastructurePreparedCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

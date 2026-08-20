package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState




@Component
class RegisterRuntimeInfrastructureCommandHandler(
    private val decision: RegisterRuntimeInfrastructureDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRuntimeInfrastructureCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

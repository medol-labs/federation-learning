package tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage.RegisterRuntimeInfrastructurePackageCommand





@Component
class RegisterRuntimeInfrastructurePackageCommandHandler(
    private val decision: RegisterRuntimeInfrastructurePackageDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRuntimeInfrastructurePackageCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand




@Component
class CreateRuntimeInstallationPlanCommandHandler(
    private val decision: CreateRuntimeInstallationPlanDecision
) {
    @CommandHandler
    fun handle(
        command: CreateRuntimeInstallationPlanCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

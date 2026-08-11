package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand




@Component
class PlanRuntimeInfrastructureCommandHandler(
    private val decision: PlanRuntimeInfrastructureDecision
) {
    @CommandHandler
    fun handle(
        command: PlanRuntimeInfrastructureCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

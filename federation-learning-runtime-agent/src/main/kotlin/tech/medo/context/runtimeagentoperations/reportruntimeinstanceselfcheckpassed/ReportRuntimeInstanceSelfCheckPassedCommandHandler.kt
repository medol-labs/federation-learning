package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand

import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState



@Component
class ReportRuntimeInstanceSelfCheckPassedCommandHandler(
    private val decision: ReportRuntimeInstanceSelfCheckPassedDecision
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeInstanceSelfCheckPassedCommand,
        @InjectEntity(idProperty = "bootstrapRequestId") state: RuntimeAgentLifecycleState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

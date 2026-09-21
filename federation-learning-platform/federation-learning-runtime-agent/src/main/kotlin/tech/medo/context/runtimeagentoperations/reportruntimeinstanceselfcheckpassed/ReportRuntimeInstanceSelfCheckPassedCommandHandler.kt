package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedService
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState
import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum


@Component
class ReportRuntimeInstanceSelfCheckPassedCommandHandler(
    private val decision: ReportRuntimeInstanceSelfCheckPassedDecision,
    private val reportRuntimeInstanceSelfCheckPassedService: ReportRuntimeInstanceSelfCheckPassedService
) {
    @CommandHandler
    fun handle(
        command: ReportRuntimeInstanceSelfCheckPassedCommand,
        @InjectEntity(idProperty = "bootstrapRequestId") state: RuntimeAgentLifecycleState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == RuntimeAgentLifecycleStateEnum.STARTED) {
            "ReportRuntimeInstanceSelfCheckPassed requires RuntimeAgentLifecycle to be Started."
        }
        val input = ReportRuntimeInstanceSelfCheckPassedInput(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope)
        val portResult = reportRuntimeInstanceSelfCheckPassedService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

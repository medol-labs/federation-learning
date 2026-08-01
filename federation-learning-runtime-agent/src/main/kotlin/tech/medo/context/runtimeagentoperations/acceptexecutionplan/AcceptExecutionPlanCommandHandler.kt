package tech.medo.runtimeagentoperations.acceptexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanInput
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class AcceptExecutionPlanCommandHandler(
    private val decision: AcceptExecutionPlanDecision,
    private val acceptExecutionPlanService: AcceptExecutionPlanService
) {
    @CommandHandler
    fun handle(
        command: AcceptExecutionPlanCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        val input = AcceptExecutionPlanInput(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle)
        val portResult = acceptExecutionPlanService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

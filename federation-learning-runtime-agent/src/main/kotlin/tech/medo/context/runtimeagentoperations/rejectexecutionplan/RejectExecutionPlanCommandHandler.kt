package tech.medo.runtimeagentoperations.rejectexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanCommand
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanInput
import tech.medo.runtimeagentoperations.rejectexecutionplan.RejectExecutionPlanService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class RejectExecutionPlanCommandHandler(
    private val decision: RejectExecutionPlanDecision,
    private val rejectExecutionPlanService: RejectExecutionPlanService
) {
    @CommandHandler
    fun handle(
        command: RejectExecutionPlanCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        val input = RejectExecutionPlanInput(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, runtimeId = command.runtimeId, localExecutionRequirementsSatisfied = command.localExecutionRequirementsSatisfied, runtimeIdentityMatched = command.runtimeIdentityMatched, runtimeDatasetBindingAvailable = command.runtimeDatasetBindingAvailable, datasetAccessValidated = command.datasetAccessValidated, baseModelAvailable = command.baseModelAvailable, trainingConfigurationSupported = command.trainingConfigurationSupported, runtimeResourceAvailable = command.runtimeResourceAvailable, runtimeAgentIdle = command.runtimeAgentIdle, rejectionReasons = command.rejectionReasons)
        val portResult = rejectExecutionPlanService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

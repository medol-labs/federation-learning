package tech.medo.runtimeagentoperations.acceptexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanCommand
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanInput
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState

import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


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
        require(state.currentState == RoundExecutionStateEnum.PLAN_RECEIVED) {
            "AcceptExecutionPlan requires RoundExecution to be PlanReceived."
        }
        val input = AcceptExecutionPlanInput(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri)
        val portResult = acceptExecutionPlanService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}

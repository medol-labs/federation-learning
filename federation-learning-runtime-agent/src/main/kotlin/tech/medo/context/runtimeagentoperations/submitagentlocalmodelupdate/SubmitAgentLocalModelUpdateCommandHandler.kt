package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateInput
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState

import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class SubmitAgentLocalModelUpdateCommandHandler(
    private val decision: SubmitAgentLocalModelUpdateDecision,
    private val submitAgentLocalModelUpdateService: SubmitAgentLocalModelUpdateService
) {
    @CommandHandler
    fun handle(
        command: SubmitAgentLocalModelUpdateCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == RoundExecutionStateEnum.COMPLETED) {
            "SubmitAgentLocalModelUpdate requires RoundExecution to be Completed."
        }
        val input = SubmitAgentLocalModelUpdateInput(modelUpdateSubmissionId = command.modelUpdateSubmissionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, roundExecutionId = command.roundExecutionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, localModelId = command.localModelId, updateArtifactId = command.updateArtifactId, artifactRef = command.artifactRef, artifactDigest = command.artifactDigest, trainingLoss = command.trainingLoss)
        val portResult = submitAgentLocalModelUpdateService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

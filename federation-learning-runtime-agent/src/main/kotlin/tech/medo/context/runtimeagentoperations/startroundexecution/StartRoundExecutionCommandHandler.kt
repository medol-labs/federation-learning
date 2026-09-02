package tech.medo.runtimeagentoperations.startroundexecution

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionCommand
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput
import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class StartRoundExecutionCommandHandler(
    private val decision: StartRoundExecutionDecision,
    private val startRoundExecutionService: StartRoundExecutionService
) {
    @CommandHandler
    fun handle(
        command: StartRoundExecutionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == RoundExecutionStateEnum.PLAN_ACCEPTED) {
            "StartRoundExecution requires RoundExecution to be PlanAccepted."
        }
        val input = StartRoundExecutionInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        val portResult = startRoundExecutionService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}

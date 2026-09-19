package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanInput
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanService
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState
import tech.medo.trainingorchestration.domain.states.ParticipantExecutionPlanStateEnum


@Component
class DispatchParticipantExecutionPlanCommandHandler(
    private val decision: DispatchParticipantExecutionPlanDecision,
    private val dispatchParticipantExecutionPlanService: DispatchParticipantExecutionPlanService
) {
    @CommandHandler
    fun handle(
        command: DispatchParticipantExecutionPlanCommand,
        @InjectEntity(idProperty = "executionPlanId") state: ParticipantExecutionPlanState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == ParticipantExecutionPlanStateEnum.PLAN_GENERATED) {
            "DispatchParticipantExecutionPlan requires ParticipantExecutionPlan to be PlanGenerated."
        }
        val input = DispatchParticipantExecutionPlanInput(executionPlanId = command.executionPlanId, executionSessionId = command.executionSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, baseModelId = command.baseModelId, baseModelArtifactUri = command.baseModelArtifactUri, baseModelRegistryRef = command.baseModelRegistryRef, baseModelPlugin = command.baseModelPlugin, baseModelFormat = command.baseModelFormat, baseModelArtifactDigest = command.baseModelArtifactDigest, baseModelSignatureUri = command.baseModelSignatureUri, runtimeEngineProfileId = command.runtimeEngineProfileId, runtimeEngineProfileName = command.runtimeEngineProfileName, runtimeEnginePluginProfile = command.runtimeEnginePluginProfile, runtimeEngineImage = command.runtimeEngineImage, runtimeEngineImageDigest = command.runtimeEngineImageDigest, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        val portResult = dispatchParticipantExecutionPlanService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

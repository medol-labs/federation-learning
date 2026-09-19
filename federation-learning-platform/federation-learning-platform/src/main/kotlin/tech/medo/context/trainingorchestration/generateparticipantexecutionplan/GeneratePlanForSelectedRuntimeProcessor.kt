package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-generate-participant-execution-plan")
@Component
class GeneratePlanForSelectedRuntimeProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundStartedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.selectedRuntimeCount > 0) {
            java.util.concurrent.CompletableFuture.allOf(*event.selectedParticipants.map { participant ->
                commandGateway.send(GenerateParticipantExecutionPlanCommand(trainingJobId = event.trainingJobId, federationId = event.federationId, federationName = event.federationName, trainingRunConfigurationId = event.trainingRunConfigurationId, configurationName = event.configurationName, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = participant.runtimeId, organizationId = participant.organizationId, baseModelId = java.util.UUID.randomUUID() /* TODO: provide baseModelId */, baseModelArtifactUri = "" /* TODO: provide baseModelArtifactUri */, baseModelRegistryRef = "" /* TODO: provide baseModelRegistryRef */, baseModelPlugin = "" /* TODO: provide baseModelPlugin */, baseModelFormat = "" /* TODO: provide baseModelFormat */, baseModelArtifactDigest = "" /* TODO: provide baseModelArtifactDigest */, baseModelSignatureUri = null /* TODO: provide baseModelSignatureUri */, runtimeEngineProfileId = java.util.UUID.randomUUID() /* TODO: provide runtimeEngineProfileId */, runtimeEngineProfileName = null /* TODO: provide runtimeEngineProfileName */, runtimeEnginePluginProfile = "" /* TODO: provide runtimeEnginePluginProfile */, runtimeEngineImage = "" /* TODO: provide runtimeEngineImage */, runtimeEngineImageDigest = null /* TODO: provide runtimeEngineImageDigest */, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
            }.toTypedArray())
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

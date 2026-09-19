package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanSelection
import java.util.UUID;


@Command
data class DispatchParticipantExecutionPlanCommand(
    val executionPlanId: UUID,
    val executionSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelPlugin: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?,
    val runtimeEngineProfileId: UUID,
    val runtimeEngineProfileName: String?,
    val runtimeEnginePluginProfile: String,
    val runtimeEngineImage: String,
    val runtimeEngineImageDigest: String?,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val publicKeyRef: String?,
    val encryptedParameterScale: Int?
) {
    @TargetEntityId
    val selection: ParticipantExecutionPlanSelection = ParticipantExecutionPlanSelection(executionPlanId = executionPlanId)

}

package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanSelection
import java.util.UUID;


@Command
data class GenerateParticipantExecutionPlanCommand(
    val executionPlanId: UUID = java.util.UUID.randomUUID(),
    val executionSessionId: UUID = java.util.UUID.randomUUID(),
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
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?
) {
    @TargetEntityId
    val selection: ParticipantExecutionPlanSelection = ParticipantExecutionPlanSelection(executionPlanId = executionPlanId)

}

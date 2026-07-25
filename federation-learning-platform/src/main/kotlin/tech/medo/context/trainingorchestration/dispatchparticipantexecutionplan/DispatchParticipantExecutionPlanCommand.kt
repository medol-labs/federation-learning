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
    val baseModelVersionId: UUID
) {
    @TargetEntityId
    val selection: ParticipantExecutionPlanSelection = ParticipantExecutionPlanSelection(executionPlanId = executionPlanId)

}

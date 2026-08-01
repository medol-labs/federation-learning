package tech.medo.trainingorchestration.dispatchparticipantexecutionplan

import java.util.UUID;

interface DispatchParticipantExecutionPlanService {
    fun supports(input: DispatchParticipantExecutionPlanInput): Boolean = true
    fun execute(input: DispatchParticipantExecutionPlanInput): DispatchParticipantExecutionPlanResult
}

data class DispatchParticipantExecutionPlanInput(
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
)

sealed interface DispatchParticipantExecutionPlanResult {
    class Succeeded : DispatchParticipantExecutionPlanResult


}

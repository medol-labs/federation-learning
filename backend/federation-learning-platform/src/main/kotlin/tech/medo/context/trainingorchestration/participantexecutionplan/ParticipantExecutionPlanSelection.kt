package tech.medo.trainingorchestration.participantexecutionplan

import java.util.UUID;


data class ParticipantExecutionPlanSelection(
    val executionPlanId: UUID
)

object ParticipantExecutionPlanTags {
    const val EXECUTION_PLAN_ID = "executionPlanId"
}

object ParticipantExecutionPlanMetadata {
    val concepts = listOf("ParticipantExecutionPlan")
}

package tech.medo.runtimeagentoperations.roundexecution

import java.util.UUID;


data class RoundExecutionSelection(
    val executionPlanId: UUID
)

object RoundExecutionTags {
    const val EXECUTION_PLAN_ID = "executionPlanId"
}

object RoundExecutionMetadata {
    val concepts = listOf("RoundExecution")
}

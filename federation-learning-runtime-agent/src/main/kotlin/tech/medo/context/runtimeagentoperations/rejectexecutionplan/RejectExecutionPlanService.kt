package tech.medo.runtimeagentoperations.rejectexecutionplan

import java.util.UUID;

interface RejectExecutionPlanService {
    fun supports(input: RejectExecutionPlanInput): Boolean = true
    fun execute(input: RejectExecutionPlanInput): RejectExecutionPlanResult
}

data class RejectExecutionPlanInput(
    val executionPlanId: UUID,
    val executionSessionId: UUID,
    val trainingJobId: UUID,
    val runtimeId: UUID,
    val localExecutionRequirementsSatisfied: Boolean,
    val runtimeIdentityMatched: Boolean,
    val runtimeDatasetBindingAvailable: Boolean,
    val datasetAccessValidated: Boolean,
    val baseModelAvailable: Boolean,
    val trainingConfigurationSupported: Boolean,
    val runtimeResourceAvailable: Boolean,
    val runtimeAgentIdle: Boolean,
    val rejectionReasons: List<String>
)

sealed interface RejectExecutionPlanResult {
    class Succeeded : RejectExecutionPlanResult


}

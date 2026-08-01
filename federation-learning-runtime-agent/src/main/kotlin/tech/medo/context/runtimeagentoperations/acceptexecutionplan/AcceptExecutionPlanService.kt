package tech.medo.runtimeagentoperations.acceptexecutionplan

import java.util.UUID;

interface AcceptExecutionPlanService {
    fun supports(input: AcceptExecutionPlanInput): Boolean = true
    fun execute(input: AcceptExecutionPlanInput): AcceptExecutionPlanResult
}

data class AcceptExecutionPlanInput(
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
    val runtimeAgentIdle: Boolean
)

sealed interface AcceptExecutionPlanResult {
    class Succeeded : AcceptExecutionPlanResult


}

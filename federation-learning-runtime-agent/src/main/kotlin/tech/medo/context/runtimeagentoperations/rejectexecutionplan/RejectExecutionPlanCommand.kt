package tech.medo.runtimeagentoperations.rejectexecutionplan

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionSelection
import java.util.UUID;


@Command
data class RejectExecutionPlanCommand(
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
) {
    @TargetEntityId
    val selection: RoundExecutionSelection = RoundExecutionSelection(executionPlanId = executionPlanId)

}

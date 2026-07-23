package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionSelection
import java.util.UUID;


@Command
data class ReleaseRuntimeEngineJobAfterStartFailureCommand(
    val roundExecutionId: UUID,
    val runtimeEngineJobId: String?,
    val executionPlanId: UUID
) {
    @TargetEntityId
    val selection: RoundExecutionSelection = RoundExecutionSelection(executionPlanId = executionPlanId)

}

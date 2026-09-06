package tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureSelection
import java.util.UUID;


@Command
data class ConfirmRuntimeInfrastructurePreparedCommand(
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val runtimeAgentId: UUID,
    val preparedNodeCount: Int,
    val preparationNotes: String?
) {
    @TargetEntityId
    val selection: RuntimeInfrastructureSelection = RuntimeInfrastructureSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}

package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureSelection
import java.util.UUID;


@Command
data class VerifyRuntimeInfrastructureCommand(
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val runtimeAgentId: UUID
) {
    @TargetEntityId
    val selection: RuntimeInfrastructureSelection = RuntimeInfrastructureSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}

package tech.medo.runtimegovernance.activateruntimeidentity

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentitySelection
import java.util.UUID;


@Command
data class ActivateRuntimeIdentityCommand(
    val runtimeId: UUID,
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeName: String
) {
    @TargetEntityId
    val selection: RuntimeIdentitySelection = RuntimeIdentitySelection(runtimeId = runtimeId)


}

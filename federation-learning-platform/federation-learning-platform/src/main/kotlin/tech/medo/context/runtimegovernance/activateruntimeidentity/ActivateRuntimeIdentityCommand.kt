package tech.medo.runtimegovernance.activateruntimeidentity

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentitySelection
import java.util.UUID;


@Command
data class ActivateRuntimeIdentityCommand(
    val runtimeId: UUID = java.util.UUID.randomUUID(),
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val organizationId: UUID,
    val runtimeName: String
) {
    @TargetEntityId
    val selection: RuntimeIdentitySelection = RuntimeIdentitySelection(runtimeId = runtimeId)

}

package tech.medo.runtimegovernance.revokeruntimeidentity

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimegovernance.runtimeidentity.RuntimeIdentitySelection
import java.util.UUID;


@Command
data class RevokeRuntimeIdentityCommand(
    val runtimeId: UUID,
    val revocationReason: String
) {
    @TargetEntityId
    val selection: RuntimeIdentitySelection = RuntimeIdentitySelection(runtimeId = runtimeId)

}

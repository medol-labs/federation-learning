package tech.medo.runtimegovernance.detectruntimecapabilities

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimegovernance.runtimecapability.RuntimeCapabilitySelection
import java.util.UUID;


@Command
data class DetectRuntimeCapabilitiesCommand(
    val runtimeId: UUID,
    val capabilityTypes: List<String>
) {
    @TargetEntityId
    val selection: RuntimeCapabilitySelection = RuntimeCapabilitySelection(runtimeId = runtimeId)

}

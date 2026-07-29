package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;


@Command
data class LoadRuntimeAgentBootstrapConfigurationCommand(
    @TargetEntityId
    val bootstrapRequestId: UUID = java.util.UUID.randomUUID()
)

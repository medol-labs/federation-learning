package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInfrastructurePreparedEvent(
    @EventTag(key = "runtimeInfrastructureId")
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val runtimeAgentId: UUID,
    val preparedNodeCount: Int,
    val preparationNotes: String?
)

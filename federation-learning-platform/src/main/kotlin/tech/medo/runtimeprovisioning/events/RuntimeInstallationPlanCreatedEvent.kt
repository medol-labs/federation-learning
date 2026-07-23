package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInstallationPlanCreatedEvent(
    @EventTag(key = "runtimeInstallationPlanId")
    val runtimeInstallationPlanId: UUID,
    val runtimeInfrastructureId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeName: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int
)

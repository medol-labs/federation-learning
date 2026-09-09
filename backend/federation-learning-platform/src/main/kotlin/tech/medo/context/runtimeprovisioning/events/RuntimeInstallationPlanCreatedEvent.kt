package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInstallationPlanCreatedEvent(
    val runtimeInstallationPlanId: UUID,
    val runtimeInfrastructureId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeName: String,
    val bootstrapCommand: String,
    val nodeLabelCommand: String,
    val nodeTaintCommand: String,
    val runtimeAgentNodeSelectorYaml: String,
    val runtimeAgentTolerationsYaml: String,
    val bootstrapConfigYaml: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int
)

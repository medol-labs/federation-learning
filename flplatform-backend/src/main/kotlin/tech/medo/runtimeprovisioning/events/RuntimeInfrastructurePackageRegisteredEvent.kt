package tech.medo.runtimeprovisioning.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInfrastructurePackageRegisteredEvent(
    val runtimeInfrastructurePackageId: UUID,
    val packageName: String,
    @EventTag(key = "packageVersion")
    val packageVersion: String,
    val runtimeEnvironmentType: String,
    val runtimeDeploymentTargetType: String,
    val installProfile: String,
    val architecture: String,
    val installGuide: String,
    @EventTag(key = "packageName")
    val packageNameEventTag: String = packageName.trim().lowercase()
)

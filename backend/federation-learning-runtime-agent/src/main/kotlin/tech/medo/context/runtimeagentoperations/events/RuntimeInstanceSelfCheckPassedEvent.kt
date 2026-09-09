package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeInstanceSelfCheckPassedEvent(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String,
    val runtimeAgentSelfCheckPassed: Boolean,
    val configurationLoaded: Boolean,
    val secretStoreAccessible: Boolean,
    val runtimeEngineAdapterReady: Boolean,
    val modelRepositoryClientReady: Boolean,
    val localDatasetBindingStoreReady: Boolean,
    val workingDirectoryWritable: Boolean,
    @EventTag(key = "bootstrapRequestId")
    val bootstrapRequestId: UUID
)

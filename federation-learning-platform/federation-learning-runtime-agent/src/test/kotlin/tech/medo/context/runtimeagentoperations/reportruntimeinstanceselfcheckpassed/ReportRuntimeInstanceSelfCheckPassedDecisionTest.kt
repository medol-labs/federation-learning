package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand
import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedResult
import java.util.UUID

class ReportRuntimeInstanceSelfCheckPassedDecisionTest {
    @Test
    fun ReportRuntimeInstanceSelfCheckPassed() {
        val state = RuntimeAgentLifecycleState()
        state.evolve(
            RuntimeAgentStartedEvent(
            runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            agentVersion = "local-dev",
            runtimeAgentEndpoint = "http://runtime-agent:8082",
            endpointScope = "CLUSTER",
            bootstrapRequestId = java.util.UUID.randomUUID()
            )
        )

        val command = ReportRuntimeInstanceSelfCheckPassedCommand(
            runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            agentVersion = "local-dev",
            runtimeAgentEndpoint = "http://runtime-agent:8082",
            endpointScope = "CLUSTER",
            bootstrapRequestId = java.util.UUID.randomUUID()
        )

        val events = (object : ReportRuntimeInstanceSelfCheckPassedDecision {}).decide(
            command,
            state = state,
            portResult = ReportRuntimeInstanceSelfCheckPassedResult.Succeeded(
                runtimeAgentSelfCheckPassed = false,
                configurationLoaded = false,
                secretStoreAccessible = false,
                runtimeEngineAdapterReady = false,
                modelRepositoryClientReady = false,
                localDatasetBindingStoreReady = false,
                workingDirectoryWritable = false
            )
        )

        val event = events.filterIsInstance<RuntimeInstanceSelfCheckPassedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()), event.runtimeAgentId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals("local-dev", event.agentVersion)
        assertEquals("http://runtime-agent:8082", event.runtimeAgentEndpoint)
        assertEquals("CLUSTER", event.endpointScope)
    }
}

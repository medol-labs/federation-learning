package tech.medo.runtimeprovisioning.recordruntimeconnectionestablished

import tech.medo.runtimeprovisioning.recordruntimeconnectionestablished.RecordRuntimeConnectionEstablishedCommand


import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





interface RecordRuntimeConnectionEstablishedDecision {
    fun decide(command: RecordRuntimeConnectionEstablishedCommand, state: RuntimeInfrastructureState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RuntimeConnectionEstablishedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, organizationId = command.organizationId, runtimeName = command.runtimeName, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope)
        )
    }
}

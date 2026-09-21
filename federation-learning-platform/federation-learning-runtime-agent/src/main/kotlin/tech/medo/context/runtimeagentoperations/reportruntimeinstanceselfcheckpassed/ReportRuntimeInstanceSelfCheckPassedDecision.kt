package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand

import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedResult
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState


import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum


interface ReportRuntimeInstanceSelfCheckPassedDecision {
    fun decide(command: ReportRuntimeInstanceSelfCheckPassedCommand, state: RuntimeAgentLifecycleState, portResult: ReportRuntimeInstanceSelfCheckPassedResult): List<Any> {
        require(state.currentState == RuntimeAgentLifecycleStateEnum.STARTED) {
            "ReportRuntimeInstanceSelfCheckPassed requires RuntimeAgentLifecycle to be Started."
        }
        return when (portResult) {
                    is ReportRuntimeInstanceSelfCheckPassedResult.Succeeded -> listOf(
            RuntimeInstanceSelfCheckPassedEvent(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion, runtimeAgentEndpoint = command.runtimeAgentEndpoint, endpointScope = command.endpointScope, runtimeAgentSelfCheckPassed = portResult.runtimeAgentSelfCheckPassed, configurationLoaded = portResult.configurationLoaded, secretStoreAccessible = portResult.secretStoreAccessible, runtimeEngineAdapterReady = portResult.runtimeEngineAdapterReady, modelRepositoryClientReady = portResult.modelRepositoryClientReady, localDatasetBindingStoreReady = portResult.localDatasetBindingStoreReady, workingDirectoryWritable = portResult.workingDirectoryWritable, bootstrapRequestId = command.bootstrapRequestId)
            )
                }
    }
}

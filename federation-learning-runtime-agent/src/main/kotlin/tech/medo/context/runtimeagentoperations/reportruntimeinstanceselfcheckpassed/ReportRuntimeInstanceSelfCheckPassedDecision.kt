package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand

import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleState


import tech.medo.runtimeagentoperations.domain.states.RuntimeAgentLifecycleStateEnum


interface ReportRuntimeInstanceSelfCheckPassedDecision {
    fun decide(command: ReportRuntimeInstanceSelfCheckPassedCommand, state: RuntimeAgentLifecycleState): List<Any> {
        require(state.currentState == RuntimeAgentLifecycleStateEnum.STARTED) {
            "ReportRuntimeInstanceSelfCheckPassed requires RuntimeAgentLifecycle to be Started."
        }
        return listOf(
            RuntimeInstanceSelfCheckPassedEvent(runtimeAgentId = command.runtimeAgentId, runtimeInfrastructureId = command.runtimeInfrastructureId, agentVersion = command.agentVersion, runtimeAgentSelfCheckPassed = command.runtimeAgentSelfCheckPassed, configurationLoaded = command.configurationLoaded, secretStoreAccessible = command.secretStoreAccessible, runtimeEngineAdapterReady = command.runtimeEngineAdapterReady, modelRepositoryClientReady = command.modelRepositoryClientReady, localDatasetBindingStoreReady = command.localDatasetBindingStoreReady, workingDirectoryWritable = command.workingDirectoryWritable, bootstrapRequestId = command.bootstrapRequestId)
        )
    }
}

package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import java.util.UUID;

interface ReportRuntimeInstanceSelfCheckPassedService {
    fun supports(input: ReportRuntimeInstanceSelfCheckPassedInput): Boolean = true
    fun execute(input: ReportRuntimeInstanceSelfCheckPassedInput): ReportRuntimeInstanceSelfCheckPassedResult
}

data class ReportRuntimeInstanceSelfCheckPassedInput(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String,
    val runtimeAgentEndpoint: String,
    val endpointScope: String
)

sealed interface ReportRuntimeInstanceSelfCheckPassedResult {
    data class Succeeded(
        val runtimeAgentSelfCheckPassed: Boolean,
        val configurationLoaded: Boolean,
        val secretStoreAccessible: Boolean,
        val runtimeEngineAdapterReady: Boolean,
        val modelRepositoryClientReady: Boolean,
        val localDatasetBindingStoreReady: Boolean,
        val workingDirectoryWritable: Boolean
    ) : ReportRuntimeInstanceSelfCheckPassedResult


}

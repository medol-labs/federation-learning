package tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.reportruntimeinstanceselfcheckpassed

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedResult
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedService

@Component
class ConfiguredReportRuntimeInstanceSelfCheckPassedAdapter : ReportRuntimeInstanceSelfCheckPassedService {
    override fun supports(input: ReportRuntimeInstanceSelfCheckPassedInput): Boolean =
        input.runtimeAgentEndpoint.isNotBlank() && input.endpointScope.isNotBlank()

    override fun execute(
        input: ReportRuntimeInstanceSelfCheckPassedInput
    ): ReportRuntimeInstanceSelfCheckPassedResult =
        ReportRuntimeInstanceSelfCheckPassedResult.Succeeded(
            runtimeAgentSelfCheckPassed = true,
            configurationLoaded = true,
            secretStoreAccessible = true,
            runtimeEngineAdapterReady = true,
            modelRepositoryClientReady = true,
            localDatasetBindingStoreReady = true,
            workingDirectoryWritable = true
        )
}

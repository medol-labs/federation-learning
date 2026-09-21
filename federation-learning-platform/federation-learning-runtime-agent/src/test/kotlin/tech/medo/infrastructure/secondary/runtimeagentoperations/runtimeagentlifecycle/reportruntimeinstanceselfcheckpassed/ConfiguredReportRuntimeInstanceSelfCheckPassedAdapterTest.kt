package tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.reportruntimeinstanceselfcheckpassed

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedResult
import java.util.UUID

class ConfiguredReportRuntimeInstanceSelfCheckPassedAdapterTest {
    private val adapter = ConfiguredReportRuntimeInstanceSelfCheckPassedAdapter()

    @Test
    fun `supports configured runtime agent endpoint`() {
        assertThat(adapter.supports(input())).isTrue()
        assertThat(adapter.supports(input(runtimeAgentEndpoint = ""))).isFalse()
        assertThat(adapter.supports(input(endpointScope = ""))).isFalse()
    }

    @Test
    fun `reports runtime instance self check as ready`() {
        val result = adapter.execute(input())

        assertThat(result).isInstanceOf(ReportRuntimeInstanceSelfCheckPassedResult.Succeeded::class.java)
        result as ReportRuntimeInstanceSelfCheckPassedResult.Succeeded
        assertThat(result.runtimeAgentSelfCheckPassed).isTrue()
        assertThat(result.configurationLoaded).isTrue()
        assertThat(result.secretStoreAccessible).isTrue()
        assertThat(result.runtimeEngineAdapterReady).isTrue()
        assertThat(result.modelRepositoryClientReady).isTrue()
        assertThat(result.localDatasetBindingStoreReady).isTrue()
        assertThat(result.workingDirectoryWritable).isTrue()
    }

    private fun input(
        runtimeAgentEndpoint: String = "http://federation-learning-runtime-agent-managed:8082",
        endpointScope: String = "CLUSTER"
    ): ReportRuntimeInstanceSelfCheckPassedInput =
        ReportRuntimeInstanceSelfCheckPassedInput(
            runtimeAgentId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            runtimeInfrastructureId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            agentVersion = "k3s",
            runtimeAgentEndpoint = runtimeAgentEndpoint,
            endpointScope = endpointScope
        )
}

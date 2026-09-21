package tech.medo.runtimeagentoperations.infrastructure.secondary.runtimeagentlifecycle.routing

import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedService
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ReportRuntimeInstanceSelfCheckPassedServiceRouter(private val adapters: ObjectProvider<ReportRuntimeInstanceSelfCheckPassedService>) : ReportRuntimeInstanceSelfCheckPassedService {
    override fun supports(input: ReportRuntimeInstanceSelfCheckPassedInput): Boolean = true

    override fun execute(input: ReportRuntimeInstanceSelfCheckPassedInput): ReportRuntimeInstanceSelfCheckPassedResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No ReportRuntimeInstanceSelfCheckPassedService adapter supports the requested input.")
            else -> error("Multiple ReportRuntimeInstanceSelfCheckPassedService adapters support the requested input.")
        }
    }
}

package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinstallationplan.routing

import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanInput
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanService
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class CreateRuntimeInstallationPlanServiceRouter(private val adapters: ObjectProvider<CreateRuntimeInstallationPlanService>) : CreateRuntimeInstallationPlanService {
    override fun supports(input: CreateRuntimeInstallationPlanInput): Boolean = true

    override fun execute(input: CreateRuntimeInstallationPlanInput): CreateRuntimeInstallationPlanResult {
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
            0 -> error("No CreateRuntimeInstallationPlanService adapter supports the requested input.")
            else -> error("Multiple CreateRuntimeInstallationPlanService adapters support the requested input.")
        }
    }
}

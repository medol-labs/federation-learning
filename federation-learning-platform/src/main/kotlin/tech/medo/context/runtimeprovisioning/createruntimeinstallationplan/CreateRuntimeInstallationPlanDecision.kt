package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import java.nio.charset.StandardCharsets
import java.util.UUID

interface CreateRuntimeInstallationPlanDecision {
    fun decide(command: CreateRuntimeInstallationPlanCommand): List<Any> {
        return listOf(
            RuntimeInstallationPlanCreatedEvent(
                runtimeInstallationPlanId = command.runtimeInstallationPlanId,
                runtimeInfrastructureId = runtimeInfrastructureIdFor(command),
                organizationId = command.organizationId,
                runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId,
                runtimeName = command.runtimeName,
                agentInstallMode = command.agentInstallMode,
                expectedNodeCount = command.expectedNodeCount
            )
        )
    }

    private fun runtimeInfrastructureIdFor(command: CreateRuntimeInstallationPlanCommand): UUID =
        UUID.nameUUIDFromBytes(
            "runtime-infrastructure:${command.runtimeInstallationPlanId}".toByteArray(StandardCharsets.UTF_8)
        )
}

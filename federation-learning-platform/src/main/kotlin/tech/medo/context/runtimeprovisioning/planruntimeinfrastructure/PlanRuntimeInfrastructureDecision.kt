package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePlannedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





interface PlanRuntimeInfrastructureDecision {
    fun decide(command: PlanRuntimeInfrastructureCommand): List<Any> {
        return listOf(
            RuntimeInfrastructurePlannedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId)
        )
    }
}

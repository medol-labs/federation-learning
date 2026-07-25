package tech.medo.runtimeprovisioning.runtimeinstallationplan

import java.util.UUID;


data class RuntimeInstallationPlanSelection(
    val runtimeInstallationPlanId: UUID
)

object RuntimeInstallationPlanTags {
    const val RUNTIME_INSTALLATION_PLAN_ID = "runtimeInstallationPlanId"
}

object RuntimeInstallationPlanMetadata {
    val concepts = listOf("RuntimeInstallationPlan")
}

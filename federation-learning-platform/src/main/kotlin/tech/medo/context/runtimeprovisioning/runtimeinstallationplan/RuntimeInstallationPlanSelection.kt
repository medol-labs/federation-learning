package tech.medo.runtimeprovisioning.runtimeinstallationplan

import java.util.UUID;


data class RuntimeInstallationPlanSelection(
    val organizationId: UUID
)

object RuntimeInstallationPlanTags {
    const val ORGANIZATION_ID = "organizationId"
}

object RuntimeInstallationPlanMetadata {
    val concepts = listOf("RuntimeInstallationPlan")
}

package tech.medo.runtimeprovisioning.domain

object Concepts {
    data object RuntimeInstallationPlan {
        const val NAME = "RuntimeInstallationPlan"
        val slices = listOf("CreateRuntimeInstallationPlan", "RuntimeInstallationPlanCatalog", "RuntimeInstallationGuide")
        val states = listOf("Planned")
    }

    data object RuntimeInfrastructurePackage {
        const val NAME = "RuntimeInfrastructurePackage"
        val slices = listOf("RegisterRuntimeInfrastructurePackage", "RuntimeInfrastructurePackageCatalog")
        val states = listOf("Registered")
    }

    data object RuntimeInfrastructure {
        const val NAME = "RuntimeInfrastructure"
        val slices = listOf("RegisterRuntimeInfrastructure", "VerifyRuntimeInfrastructure", "DeployRuntimeAgent", "RetryRuntimeAgentDeployment", "RecordRuntimeConnectionEstablished", "RuntimeInfrastructureAccessView")
        val states = listOf("Registered", "Verified", "VerificationFailed", "AgentReady", "RuntimeAgentFailed", "Offline", "Connected")
    }
}

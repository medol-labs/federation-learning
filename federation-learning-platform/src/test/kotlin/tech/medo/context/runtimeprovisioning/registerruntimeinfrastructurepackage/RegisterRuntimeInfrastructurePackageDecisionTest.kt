package tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.registerruntimeinfrastructurepackage.RegisterRuntimeInfrastructurePackageCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent



import java.util.UUID;


class RegisterRuntimeInfrastructurePackageDecisionTest {
    @Test
    fun RegisterRuntimeInfrastructurePackageEmitsRuntimeInfrastructurePackageRegisteredEvent() {
        val events = (object : RegisterRuntimeInfrastructurePackageDecision {}).decide(
            RegisterRuntimeInfrastructurePackageCommand(
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            packageName = "",
            packageVersion = "",
            runtimeEnvironmentType = "",
            runtimeDeploymentTargetType = "",
            installProfile = "",
            architecture = "",
            installGuide = ""
            )
        )

        assertTrue(events.any { it is RuntimeInfrastructurePackageRegisteredEvent })
    }
}

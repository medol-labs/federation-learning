package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent



import java.util.UUID;


class RegisterRuntimeInfrastructureDecisionTest {
    @Test
    fun RegisterRuntimeInfrastructureEmitsRuntimeInfrastructureRegisteredEvent() {
        val events = (object : RegisterRuntimeInfrastructureDecision {}).decide(
            RegisterRuntimeInfrastructureCommand(
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID()
            )
        )

        assertTrue(events.any { it is RuntimeInfrastructureRegisteredEvent })
    }
}

package tech.medo.runtimegovernance.activateruntimeidentity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import java.util.UUID

class ActivateRuntimeIdentityDecisionTest {
    @Test
    fun ActivateRuntimeIdentityEmitsRuntimeIdentityActivatedEvent() {
        val events = (object : ActivateRuntimeIdentityDecision {}).decide(
            ActivateRuntimeIdentityCommand(
            runtimeId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeName = ""
            )
        )

        assertTrue(events.any { it is RuntimeIdentityActivatedEvent })
    }
}

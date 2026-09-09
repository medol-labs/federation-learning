package tech.medo.runtimeprovisioning.planruntimeinfrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.planruntimeinfrastructure.PlanRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePlannedEvent
import java.util.UUID

class PlanRuntimeInfrastructureDecisionTest {
    @Test
    fun PlanRuntimeInfrastructureEmitsRuntimeInfrastructurePlannedEvent() {
        val events = (object : PlanRuntimeInfrastructureDecision {}).decide(
            PlanRuntimeInfrastructureCommand(
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeInstallationPlanId = java.util.UUID.randomUUID()
            )
        )

        assertTrue(events.any { it is RuntimeInfrastructurePlannedEvent })
    }
}

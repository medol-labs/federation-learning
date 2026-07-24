package tech.medo.modellifecycle.promotemodeltoproduction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modellifecycle.promotemodeltoproduction.PromoteModelToProductionCommand
import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent

import tech.medo.modellifecycle.modelversion.ModelVersionState

import java.util.UUID;


class PromoteModelToProductionDecisionTest {
    @Test
    fun PromoteApprovedModel() {
        val state = ModelVersionState()
        state.evolve(
            ModelApprovedEvent(
            modelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            approvalNote = null
            )
        )

        val command = PromoteModelToProductionCommand(
            modelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            releaseChannel = "stable",
            productionStage = "production"
        )

        val events = PromoteModelToProductionDecision().decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelPromotedToProductionEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelVersionId)
        assertEquals("stable", event.releaseChannel)
        assertEquals("production", event.productionStage)
    }
}

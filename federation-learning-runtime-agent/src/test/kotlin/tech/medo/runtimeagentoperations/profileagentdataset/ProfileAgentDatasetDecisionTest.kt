package tech.medo.runtimeagentoperations.profileagentdataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent



import java.util.UUID;
import java.math.BigDecimal;


class ProfileAgentDatasetDecisionTest {
    @Test
    fun ProfileAgentDatasetEmitsAgentDatasetMetadataReportedEvent() {
        val events = ProfileAgentDatasetDecision().decide(
            ProfileAgentDatasetCommand(
            metadataReportId = java.util.UUID.randomUUID(),
            runtimeDatasetBindingId = java.util.UUID.randomUUID()
            )
        )

        assertTrue(events.any { it is AgentDatasetMetadataReportedEvent })
    }
}

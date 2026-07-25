package tech.medo.runtimemonitoring.raisetrainingalert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimemonitoring.raisetrainingalert.RaiseTrainingAlertCommand
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent



import java.util.UUID;


class RaiseTrainingAlertDecisionTest {
    @Test
    fun RaiseTrainingAlertEmitsTrainingAlertRaisedEvent() {
        val events = RaiseTrainingAlertDecision().decide(
            RaiseTrainingAlertCommand(
            alertId = java.util.UUID.randomUUID(),
            nodeId = java.util.UUID.randomUUID(),
            trainingJobId = null,
            severity = "",
            message = ""
            )
        )

        assertTrue(events.any { it is TrainingAlertRaisedEvent })
    }
}

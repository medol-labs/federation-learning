package tech.medo.modellifecycle.registercandidatemodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand
import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent



import java.util.UUID;
import java.math.BigDecimal;


class RegisterCandidateModelDecisionTest {
    @Test
    fun RegisterCandidateModelEmitsModelCandidateRegisteredEvent() {
        val events = (object : RegisterCandidateModelDecision {}).decide(
            RegisterCandidateModelCommand(
            modelVersionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            finalRoundId = java.util.UUID.randomUUID(),
            modelArtifactId = java.util.UUID.randomUUID(),
            modelHash = "",
            evaluationReportId = java.util.UUID.randomUUID(),
            finalGlobalAccuracy = java.math.BigDecimal.ZERO
            )
        )

        assertTrue(events.any { it is ModelCandidateRegisteredEvent })
    }
}

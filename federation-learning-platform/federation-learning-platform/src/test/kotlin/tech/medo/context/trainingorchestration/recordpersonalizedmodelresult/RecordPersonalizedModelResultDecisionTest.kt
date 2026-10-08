package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand
import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import java.util.UUID
import java.math.BigDecimal

class RecordPersonalizedModelResultDecisionTest {
    @Test
    fun RecordPersonalizedArtifactFromAcceptedUpdate() {


        val command = RecordPersonalizedModelResultCommand(
            personalizedModelId = UUID.nameUUIDFromBytes("personalized-model-1".toByteArray()),
            modelUpdateSubmissionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            personalizationMode = null,
            personalizationScope = null,
            personalizedArtifactPolicy = null,
            personalizationMetric = null,
            minimumPersonalizationLift = null,
            personalizationTargetType = null,
            personalizationTargetId = null,
            personalizationCohortId = null,
            personalizationProfileId = null,
            personalizedModelArtifactRef = "",
            personalizedModelArtifactDigest = "",
            personalizedMetricsArtifactRef = null,
            personalizedAccuracy = null,
            personalizationLift = null
        )

        val events = (object : RecordPersonalizedModelResultDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<PersonalizedModelResultRecordedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("personalized-model-1".toByteArray()), event.personalizedModelId)
        assertEquals(command.modelUpdateSubmissionId, event.modelUpdateSubmissionId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.trainingRunConfigurationId, event.trainingRunConfigurationId)
        assertEquals(command.roundId, event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.runtimeId, event.runtimeId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(command.personalizationMode, event.personalizationMode)
        assertEquals(command.personalizationScope, event.personalizationScope)
        assertEquals(command.personalizedArtifactPolicy, event.personalizedArtifactPolicy)
        assertEquals(command.personalizationMetric, event.personalizationMetric)
        assertEquals(command.minimumPersonalizationLift, event.minimumPersonalizationLift)
        assertEquals(command.personalizationTargetType, event.personalizationTargetType)
        assertEquals(command.personalizationTargetId, event.personalizationTargetId)
        assertEquals(command.personalizationCohortId, event.personalizationCohortId)
        assertEquals(command.personalizationProfileId, event.personalizationProfileId)
        assertEquals(command.personalizedModelArtifactRef, event.personalizedModelArtifactRef)
        assertEquals(command.personalizedModelArtifactDigest, event.personalizedModelArtifactDigest)
        assertEquals(command.personalizedMetricsArtifactRef, event.personalizedMetricsArtifactRef)
        assertEquals(command.personalizedAccuracy, event.personalizedAccuracy)
        assertEquals(command.personalizationLift, event.personalizationLift)
    }
}

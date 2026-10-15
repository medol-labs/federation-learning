package tech.medo.domain.trainingorchestration.recordpersonalizedmodelresult

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand
import java.math.BigDecimal
import java.util.UUID

class RecordPersonalizedModelResultDecisionComponentTest {
    private fun command() = RecordPersonalizedModelResultCommand(
        personalizedModelId = UUID.randomUUID(), modelUpdateSubmissionId = UUID.randomUUID(),
        trainingJobId = UUID.randomUUID(), trainingRunConfigurationId = UUID.randomUUID(), roundId = UUID.randomUUID(),
        roundNumber = 1, runtimeId = UUID.randomUUID(), featureSchemaId = UUID.randomUUID(),
        personalizationMode = "LOCAL_ADAPTATION", personalizationScope = "PARTICIPANT",
        personalizedArtifactPolicy = "REGISTER_RUNTIME_LOCAL", personalizationMetric = "ACCURACY",
        minimumPersonalizationLift = BigDecimal.ZERO, personalizationTargetType = "PARTICIPANT",
        personalizationTargetId = UUID.randomUUID(), personalizationCohortId = null, personalizationProfileId = null,
        personalizedModelArtifactRef = "file:///runtime/personalized.pt", personalizedModelArtifactDigest = "sha256:" + "a".repeat(64),
        personalizedModelStorageLocation = "REGISTER_RUNTIME_LOCAL", platformManagedArtifactRef = "REGISTER_RUNTIME_LOCAL",
        personalizedMetricsArtifactRef = "file:///runtime/metrics.json", personalizedAccuracy = BigDecimal("0.8"),
        personalizationLift = BigDecimal("0.1")
    )

    @Test
    fun recordsRuntimeStorageWithoutClaimingPlatformCustody() {
        val result = RecordPersonalizedModelResultDecisionComponent().decide(command()).single() as PersonalizedModelResultRecordedEvent
        assertEquals("RUNTIME_LOCAL", result.personalizedModelStorageLocation)
        assertNull(result.platformManagedArtifactRef)
        assertEquals(BigDecimal("0.8"), result.personalizedAccuracy)
    }

    @Test
    fun rejectsMissingTargetAndInvalidDigest() {
        val decision = RecordPersonalizedModelResultDecisionComponent()
        assertThrows(IllegalArgumentException::class.java) { decision.decide(command().copy(personalizationTargetId = null)) }
        assertThrows(IllegalArgumentException::class.java) { decision.decide(command().copy(personalizedModelArtifactDigest = "sha256:fake")) }
        assertThrows(IllegalArgumentException::class.java) { decision.decide(command().copy(personalizedArtifactPolicy = "UPLOAD_TO_PLATFORM")) }
    }
}

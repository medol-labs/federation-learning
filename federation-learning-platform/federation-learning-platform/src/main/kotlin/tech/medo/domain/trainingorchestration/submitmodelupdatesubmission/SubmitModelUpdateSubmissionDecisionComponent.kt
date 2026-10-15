package tech.medo.domain.trainingorchestration.submitmodelupdatesubmission

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionDecision
import tech.medo.trainingorchestration.submitmodelupdatesubmission.SubmitModelUpdateSubmissionCommand
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.math.BigDecimal

@Component
class SubmitModelUpdateSubmissionDecisionComponent : SubmitModelUpdateSubmissionDecision {
    override fun decide(command: SubmitModelUpdateSubmissionCommand, state: TrainingRoundState): List<Any> {
        val expectedMode = state.personalizationMode ?: command.personalizationMode ?: "GLOBAL_ONLY"
        if (expectedMode != "GLOBAL_ONLY") {
            require(command.personalizationMode == expectedMode) { "Model update personalization mode does not match the training round." }
            require(command.personalizationMode == "LOCAL_ADAPTATION") { "Unsupported personalization mode." }
            require(command.personalizedArtifactAvailable) { "Personalized execution must report a completed personalized model artifact." }
            require(command.personalizationTargetType == "PARTICIPANT" && command.personalizationTargetId == command.runtimeId) {
                "Personalized result target must match the contributing runtime."
            }
            require(command.personalizedArtifactPolicy in setOf("REGISTER_RUNTIME_LOCAL", "KEEP_AT_RUNTIME")) { "Unsupported personalized artifact policy." }
            require(command.personalizedModelId != null && !command.personalizedModelArtifactRef.isNullOrBlank()) { "Personalized model identity and reference are required." }
            require(command.personalizedModelArtifactDigest?.matches(Regex("sha256:[a-fA-F0-9]{64}")) == true) { "Personalized model digest is required." }
            val accuracy = requireNotNull(command.personalizedAccuracy) { "Personalized evaluation is required." }
            val lift = requireNotNull(command.personalizationLift) { "Personalization lift is required." }
            require(accuracy >= BigDecimal.ZERO && accuracy <= BigDecimal.ONE) { "Personalized accuracy must be between zero and one." }
            require(command.minimumPersonalizationLift == null || lift >= command.minimumPersonalizationLift) { "Personalization lift is below the configured minimum." }
        } else {
            require(!command.personalizedArtifactAvailable) { "Global-only execution must not register a personalized artifact." }
        }
        return super.decide(command, state)
    }
}

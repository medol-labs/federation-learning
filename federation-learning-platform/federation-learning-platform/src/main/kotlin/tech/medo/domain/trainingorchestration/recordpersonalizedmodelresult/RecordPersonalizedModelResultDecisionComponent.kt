package tech.medo.domain.trainingorchestration.recordpersonalizedmodelresult

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultDecision
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand

@Component
class RecordPersonalizedModelResultDecisionComponent : RecordPersonalizedModelResultDecision {
    override fun decide(command: RecordPersonalizedModelResultCommand): List<Any> {
        require(command.personalizedArtifactPolicy in setOf("REGISTER_RUNTIME_LOCAL", "KEEP_AT_RUNTIME")) {
            "Unsupported personalized artifact storage policy: ${command.personalizedArtifactPolicy}"
        }
        require(command.personalizationTargetId != null && !command.personalizationTargetType.isNullOrBlank()) {
            "Personalized model registration requires a target."
        }
        require(command.personalizedModelArtifactRef.startsWith("file:") || command.personalizedModelArtifactRef.startsWith("runtime-artifact:")) {
            "Runtime-local personalized models must reference a runtime-owned artifact."
        }
        require(command.personalizedModelArtifactDigest.matches(Regex("sha256:[a-fA-F0-9]{64}"))) {
            "Personalized model registration requires a SHA-256 content digest."
        }
        return super.decide(command.copy(personalizedModelStorageLocation = "RUNTIME_LOCAL", platformManagedArtifactRef = null))
    }
}

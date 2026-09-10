package tech.medo.domain.secureaggregation.recordencryptedmodelupdate

import org.springframework.stereotype.Component
import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand
import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateDecision
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState

@Component
class RecordEncryptedModelUpdateDecisionComponent : RecordEncryptedModelUpdateDecision {
    override fun decide(
        command: RecordEncryptedModelUpdateCommand,
        state: SecureAggregationSessionState
    ): List<Any> {
        val receivedRuntimeIds = (state.receivedRuntimeIds + command.runtimeId).distinct()
        val receivedArtifactRefs =
            (state.receivedEncryptedUpdateArtifactRefs + command.encryptedUpdateArtifactRef).distinct()
        return listOf(
            EncryptedModelUpdateReceivedEvent(
                secureAggregationSessionId = command.secureAggregationSessionId,
                submissionId = command.submissionId,
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                trainingJobObjective = command.trainingJobObjective,
                featureSchemaId = command.featureSchemaId,
                roundId = command.roundId,
                roundNumber = command.roundNumber,
                maxRounds = command.maxRounds,
                minimumAccuracy = command.minimumAccuracy,
                runtimeId = command.runtimeId,
                updateArtifactId = command.updateArtifactId,
                encryptedUpdateArtifactRef = command.encryptedUpdateArtifactRef,
                encryptedUpdateDigest = command.encryptedUpdateDigest,
                encryptionScheme = command.encryptionScheme,
                publicKeyVersion = command.publicKeyVersion,
                receivedEncryptedUpdateCount = receivedRuntimeIds.size,
                receivedRuntimeIds = receivedRuntimeIds,
                receivedEncryptedUpdateArtifactRefs = receivedArtifactRefs,
                selectedParticipantCount = requireNotNull(state.selectedParticipantCount) {
                    "selectedParticipantCount is required from secure aggregation state."
                }
            )
        )
    }
}

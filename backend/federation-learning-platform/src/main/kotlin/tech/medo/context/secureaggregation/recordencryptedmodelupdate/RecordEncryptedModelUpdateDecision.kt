package tech.medo.secureaggregation.recordencryptedmodelupdate

import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand


import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





interface RecordEncryptedModelUpdateDecision {
    fun decide(command: RecordEncryptedModelUpdateCommand, state: SecureAggregationSessionState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            EncryptedModelUpdateReceivedEvent(secureAggregationSessionId = command.secureAggregationSessionId, submissionId = command.submissionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, runtimeId = command.runtimeId, updateArtifactId = command.updateArtifactId, encryptedUpdateArtifactRef = command.encryptedUpdateArtifactRef, encryptedUpdateDigest = command.encryptedUpdateDigest, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, receivedEncryptedUpdateCount = 0 /* TODO: count(appendDistinct(EncryptedModelUpdateReceived.receivedRuntimeIds, RecordEncryptedModelUpdate.runtimeId)) */, receivedRuntimeIds = emptyList() /* TODO: appendDistinct(EncryptedModelUpdateReceived.receivedRuntimeIds, RecordEncryptedModelUpdate.runtimeId) */, receivedEncryptedUpdateArtifactRefs = emptyList() /* TODO: appendDistinct(EncryptedModelUpdateReceived.receivedEncryptedUpdateArtifactRefs, RecordEncryptedModelUpdate.encryptedUpdateArtifactRef) */, selectedParticipantCount = requireNotNull(state.selectedParticipantCount) { "selectedParticipantCount is required from state." })
        )
    }
}

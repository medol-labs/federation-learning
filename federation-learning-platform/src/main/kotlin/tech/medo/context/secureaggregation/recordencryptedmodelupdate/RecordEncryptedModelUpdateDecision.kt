package tech.medo.secureaggregation.recordencryptedmodelupdate

import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand

import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





interface RecordEncryptedModelUpdateDecision {
    fun decide(command: RecordEncryptedModelUpdateCommand, state: SecureAggregationSessionState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            EncryptedModelUpdateReceivedEvent(secureAggregationSessionId = command.secureAggregationSessionId, submissionId = command.submissionId, runtimeId = command.runtimeId, encryptedUpdateDigest = command.encryptedUpdateDigest)
        )
    }
}

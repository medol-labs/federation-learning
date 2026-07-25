package tech.medo.secureaggregation.recordencryptedmodelupdate

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class RecordEncryptedModelUpdateCommand(
    val secureAggregationSessionId: UUID,
    val submissionId: UUID,
    val runtimeId: UUID,
    val encryptedUpdateDigest: String
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

package tech.medo.secureaggregation.recordencryptedmodelupdate

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class RecordEncryptedModelUpdateCommand(
    val secureAggregationSessionId: UUID,
    val submissionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val updateArtifactId: UUID,
    val encryptedUpdateArtifactRef: String,
    val encryptedUpdateDigest: String,
    val encryptionScheme: String,
    val publicKeyVersion: String
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}

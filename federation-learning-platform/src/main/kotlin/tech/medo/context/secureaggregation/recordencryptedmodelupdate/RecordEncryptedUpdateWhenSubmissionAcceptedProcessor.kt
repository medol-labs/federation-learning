package tech.medo.secureaggregation.recordencryptedmodelupdate

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class RecordEncryptedUpdateWhenSubmissionAcceptedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.updateProtectionType == "HOMOMORPHIC_ENCRYPTED") {
            commandGateway.send(RecordEncryptedModelUpdateCommand(secureAggregationSessionId = event.secureAggregationSessionId!!, submissionId = event.modelUpdateSubmissionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, runtimeId = event.runtimeId, updateArtifactId = event.updateArtifactId, encryptedUpdateArtifactRef = event.artifactRef, encryptedUpdateDigest = event.artifactDigest, encryptionScheme = event.encryptionScheme!!, publicKeyVersion = event.publicKeyVersion!!)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

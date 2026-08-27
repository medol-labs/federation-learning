package tech.medo.trainingorchestration.starttraininground

import tech.medo.secureaggregation.events.HomomorphicEncryptionContextPreparedEvent
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class StartRoundWhenEncryptionContextPreparedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: HomomorphicEncryptionContextPreparedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.selectedRuntimeCount >= event.minimumNodesPerRound) {
            commandGateway.send(StartTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = event.encryptionScheme, publicKeyVersion = event.publicKeyVersion, publicKeyRef = event.publicKeyRef, encryptedParameterScale = event.encryptedParameterScale)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

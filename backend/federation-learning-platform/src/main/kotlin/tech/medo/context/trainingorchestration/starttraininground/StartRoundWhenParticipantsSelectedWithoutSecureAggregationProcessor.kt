package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class StartRoundWhenParticipantsSelectedWithoutSecureAggregationProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.secureAggregationRequired == false) {
            commandGateway.send(StartTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, aggregationAlgorithm = event.aggregationAlgorithm, secureAggregationRequired = event.secureAggregationRequired, secureAggregationSessionId = null /* TODO: provide secureAggregationSessionId */, encryptionScheme = null /* TODO: provide encryptionScheme */, publicKeyVersion = null /* TODO: provide publicKeyVersion */, publicKeyRef = null /* TODO: provide publicKeyRef */, encryptedParameterScale = null /* TODO: provide encryptedParameterScale */)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

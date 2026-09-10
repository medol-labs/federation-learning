package tech.medo.trainingorchestration.requestsecureaggregation

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class RequestAggregationWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.secureAggregationRequired == true) {
            commandGateway.send(RequestSecureAggregationCommand(trainingJobId = event.trainingJobId, federationId = event.federationId, federationName = event.federationName, trainingRunConfigurationId = event.trainingRunConfigurationId, configurationName = event.configurationName, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, featureDomain = event.featureDomain, featureSchemaVersion = event.featureSchemaVersion, roundId = event.roundId, roundNumber = event.roundNumber, requiredParticipantCount = event.selectedRuntimeCount, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, secureAggregationRequired = event.secureAggregationRequired)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

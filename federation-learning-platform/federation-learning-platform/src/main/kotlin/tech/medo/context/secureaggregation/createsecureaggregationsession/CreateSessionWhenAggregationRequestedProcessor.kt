package tech.medo.secureaggregation.createsecureaggregationsession

import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-secure-aggregation-create-secure-aggregation-session")
@Component
class CreateSessionWhenAggregationRequestedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationRequestedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CreateSecureAggregationSessionCommand(trainingJobId = event.trainingJobId, federationId = event.federationId, federationName = event.federationName, trainingRunConfigurationId = event.trainingRunConfigurationId, configurationName = event.configurationName, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, featureDomain = event.featureDomain, featureSchemaVersion = event.featureSchemaVersion, roundId = event.roundId, roundNumber = event.roundNumber, requiredParticipantCount = event.requiredParticipantCount, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, secureAggregationRequired = event.secureAggregationRequired)).resultMessage
}

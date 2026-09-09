package tech.medo.secureaggregation.selectsecureaggregationparticipants

import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SelectParticipantsWhenSessionCreatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationSessionCreatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SelectSecureAggregationParticipantsCommand(secureAggregationSessionId = event.secureAggregationSessionId, roundId = event.roundId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedParticipantCount = event.requiredParticipantCount, minimumNodesPerRound = event.minimumNodesPerRound, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, secureAggregationRequired = event.secureAggregationRequired)).resultMessage
}

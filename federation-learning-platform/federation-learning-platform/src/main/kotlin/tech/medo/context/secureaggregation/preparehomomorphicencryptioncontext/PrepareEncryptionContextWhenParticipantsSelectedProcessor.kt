package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-secure-aggregation-prepare-homomorphic-encryption-context")
@Component
class PrepareEncryptionContextWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(PrepareHomomorphicEncryptionContextCommand(secureAggregationSessionId = event.secureAggregationSessionId, trainingJobId = event.trainingJobId, federationId = event.federationId, federationName = event.federationName, trainingRunConfigurationId = event.trainingRunConfigurationId, configurationName = event.configurationName, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, featureDomain = event.featureDomain, featureSchemaVersion = event.featureSchemaVersion, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedParticipantCount, minimumNodesPerRound = event.minimumNodesPerRound, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, secureAggregationRequired = event.secureAggregationRequired)).resultMessage
}

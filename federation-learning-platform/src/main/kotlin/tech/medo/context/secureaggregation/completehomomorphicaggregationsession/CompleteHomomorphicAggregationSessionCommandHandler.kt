package tech.medo.secureaggregation.completehomomorphicaggregationsession

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionService
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState




@Component
class CompleteHomomorphicAggregationSessionCommandHandler(
    private val decision: CompleteHomomorphicAggregationSessionDecision,
    private val completeHomomorphicAggregationSessionService: CompleteHomomorphicAggregationSessionService
) {
    @CommandHandler
    fun handle(
        command: CompleteHomomorphicAggregationSessionCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        val input = CompleteHomomorphicAggregationSessionInput(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri)
        val portResult = completeHomomorphicAggregationSessionService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

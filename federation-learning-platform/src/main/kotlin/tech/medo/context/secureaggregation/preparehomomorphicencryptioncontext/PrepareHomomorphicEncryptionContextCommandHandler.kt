package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextInput
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextService
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState

import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


@Component
class PrepareHomomorphicEncryptionContextCommandHandler(
    private val decision: PrepareHomomorphicEncryptionContextDecision,
    private val prepareHomomorphicEncryptionContextService: PrepareHomomorphicEncryptionContextService
) {
    @CommandHandler
    fun handle(
        command: PrepareHomomorphicEncryptionContextCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED) {
            "PrepareHomomorphicEncryptionContext requires SecureAggregationSession to be ParticipantsSelected."
        }
        val input = PrepareHomomorphicEncryptionContextInput(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, secureAggregationRequired = command.secureAggregationRequired)
        val portResult = prepareHomomorphicEncryptionContextService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsInput
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsService
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


@Component
class SelectSecureAggregationParticipantsCommandHandler(
    private val decision: SelectSecureAggregationParticipantsDecision,
    private val selectSecureAggregationParticipantsService: SelectSecureAggregationParticipantsService
) {
    @CommandHandler
    fun handle(
        command: SelectSecureAggregationParticipantsCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == SecureAggregationSessionStateEnum.PLANNED) {
            "SelectSecureAggregationParticipants requires SecureAggregationSession to be Planned."
        }
        val input = SelectSecureAggregationParticipantsInput(secureAggregationSessionId = command.secureAggregationSessionId, roundId = command.roundId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedParticipantCount = command.selectedParticipantCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired)
        val portResult = selectSecureAggregationParticipantsService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

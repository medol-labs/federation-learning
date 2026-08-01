package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsInput
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsService
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState



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
        val input = SelectSecureAggregationParticipantsInput(secureAggregationSessionId = command.secureAggregationSessionId, roundId = command.roundId, acceptedRuntimeIds = command.acceptedRuntimeIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipantCount = command.selectedParticipantCount)
        val portResult = selectSecureAggregationParticipantsService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

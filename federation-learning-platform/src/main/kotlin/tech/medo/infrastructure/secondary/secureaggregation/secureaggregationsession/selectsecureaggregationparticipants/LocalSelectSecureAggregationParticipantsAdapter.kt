package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.selectsecureaggregationparticipants

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsInput
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsResult
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsService

@Component
class LocalSelectSecureAggregationParticipantsAdapter : SelectSecureAggregationParticipantsService {
    private val log = LoggerFactory.getLogger(LocalSelectSecureAggregationParticipantsAdapter::class.java)

    override fun supports(input: SelectSecureAggregationParticipantsInput): Boolean = true

    override fun execute(input: SelectSecureAggregationParticipantsInput): SelectSecureAggregationParticipantsResult {
        require(input.acceptedRuntimeIds.isNotEmpty()) {
            "Secure aggregation participant selection requires at least one accepted runtime."
        }
        require(input.selectedRuntimeIds.isNotEmpty()) {
            "Secure aggregation participant selection requires selected runtime ids."
        }
        require(input.selectedParticipantCount > 0) {
            "Secure aggregation participant selection requires a positive selected participant count."
        }

        log.info(
            "Selected secure aggregation participants. secureAggregationSessionId={}, roundId={}, selectedParticipantCount={}, selectedRuntimeIds={}",
            input.secureAggregationSessionId,
            input.roundId,
            input.selectedParticipantCount,
            input.selectedRuntimeIds
        )
        return SelectSecureAggregationParticipantsResult.Succeeded()
    }
}

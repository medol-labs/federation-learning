package tech.medo.secureaggregation.infrastructure.secondary.secureaggregationsession.routing

import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsInput
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsService
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SelectSecureAggregationParticipantsServiceRouter(private val adapters: ObjectProvider<SelectSecureAggregationParticipantsService>) : SelectSecureAggregationParticipantsService {
    override fun supports(input: SelectSecureAggregationParticipantsInput): Boolean = true

    override fun execute(input: SelectSecureAggregationParticipantsInput): SelectSecureAggregationParticipantsResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No SelectSecureAggregationParticipantsService adapter supports the requested input.")
            else -> error("Multiple SelectSecureAggregationParticipantsService adapters support the requested input.")
        }
    }
}

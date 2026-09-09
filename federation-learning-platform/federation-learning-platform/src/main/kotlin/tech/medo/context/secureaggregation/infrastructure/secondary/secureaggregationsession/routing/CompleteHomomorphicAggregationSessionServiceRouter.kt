package tech.medo.secureaggregation.infrastructure.secondary.secureaggregationsession.routing

import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionService
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class CompleteHomomorphicAggregationSessionServiceRouter(private val adapters: ObjectProvider<CompleteHomomorphicAggregationSessionService>) : CompleteHomomorphicAggregationSessionService {
    override fun supports(input: CompleteHomomorphicAggregationSessionInput): Boolean = true

    override fun execute(input: CompleteHomomorphicAggregationSessionInput): CompleteHomomorphicAggregationSessionResult {
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
            0 -> error("No CompleteHomomorphicAggregationSessionService adapter supports the requested input.")
            else -> error("Multiple CompleteHomomorphicAggregationSessionService adapters support the requested input.")
        }
    }
}

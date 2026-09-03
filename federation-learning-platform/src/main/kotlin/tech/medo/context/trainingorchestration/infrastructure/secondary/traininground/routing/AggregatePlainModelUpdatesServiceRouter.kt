package tech.medo.trainingorchestration.infrastructure.secondary.traininground.routing

import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesInput
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesService
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class AggregatePlainModelUpdatesServiceRouter(private val adapters: ObjectProvider<AggregatePlainModelUpdatesService>) : AggregatePlainModelUpdatesService {
    override fun supports(input: AggregatePlainModelUpdatesInput): Boolean = true

    override fun execute(input: AggregatePlainModelUpdatesInput): AggregatePlainModelUpdatesResult {
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
            0 -> error("No AggregatePlainModelUpdatesService adapter supports the requested input.")
            else -> error("Multiple AggregatePlainModelUpdatesService adapters support the requested input.")
        }
    }
}

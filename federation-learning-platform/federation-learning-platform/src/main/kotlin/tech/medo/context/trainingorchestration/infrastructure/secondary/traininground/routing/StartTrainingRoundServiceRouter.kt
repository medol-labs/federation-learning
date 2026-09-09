package tech.medo.trainingorchestration.infrastructure.secondary.traininground.routing

import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundInput
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundService
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class StartTrainingRoundServiceRouter(private val adapters: ObjectProvider<StartTrainingRoundService>) : StartTrainingRoundService {
    override fun supports(input: StartTrainingRoundInput): Boolean = true

    override fun execute(input: StartTrainingRoundInput): StartTrainingRoundResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                StartTrainingRoundResult.Unavailable(
                    failureReason = ex.message ?: "StartTrainingRoundService is unavailable."
                )
            }
            0 -> StartTrainingRoundResult.Unavailable(
                failureReason = "No StartTrainingRoundService adapter supports the requested input."
            )
            else -> StartTrainingRoundResult.Unavailable(
                failureReason = "Multiple StartTrainingRoundService adapters support the requested input."
            )
        }
    }
}

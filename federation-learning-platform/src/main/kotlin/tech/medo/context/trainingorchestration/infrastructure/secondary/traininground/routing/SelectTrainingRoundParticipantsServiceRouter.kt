package tech.medo.trainingorchestration.infrastructure.secondary.traininground.routing

import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsInput
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsService
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SelectTrainingRoundParticipantsServiceRouter(private val adapters: ObjectProvider<SelectTrainingRoundParticipantsService>) : SelectTrainingRoundParticipantsService {
    override fun supports(input: SelectTrainingRoundParticipantsInput): Boolean = true

    override fun execute(input: SelectTrainingRoundParticipantsInput): SelectTrainingRoundParticipantsResult {
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
            0 -> error("No SelectTrainingRoundParticipantsService adapter supports the requested input.")
            else -> error("Multiple SelectTrainingRoundParticipantsService adapters support the requested input.")
        }
    }
}

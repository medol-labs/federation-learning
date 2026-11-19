package tech.medo.domain.trainingorchestration.starttraininground

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundDecision
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelCriteria
import org.springframework.data.domain.PageRequest
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.StringFilter
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum
import java.time.LocalDateTime

@Component
class StartTrainingRoundDecisionComponent(private val rounds: TrainingRoundProgressReadModelRepository) : StartTrainingRoundDecision {
    override fun decide(command: StartTrainingRoundCommand, state: TrainingRoundState, portResult: StartTrainingRoundResult, now: LocalDateTime): List<Any> {
        if (state.currentState in setOf(TrainingRoundStateEnum.Running, TrainingRoundStateEnum.EvaluatingGlobalModel, TrainingRoundStateEnum.Completed)) {
            return emptyList()
        }
        if (state.currentState == null) {
            // Before round tags, several physical IDs could be allocated for the same logical slot.
            val startedSlot = TrainingRoundProgressReadModelCriteria().apply {
                trainingJobId = StringFilter().also { it.equals = command.trainingJobId.toString() }
                roundNumber = IntegerFilter().also { it.equals = command.roundNumber }
                this.state = Filter<TrainingRoundStateEnum>().also { it.`in` = listOf(TrainingRoundStateEnum.Running, TrainingRoundStateEnum.EvaluatingGlobalModel, TrainingRoundStateEnum.Completed) }
            }
            if (rounds.findAllByCriteria(startedSlot, PageRequest.of(0, 1)).hasContent()) return emptyList()
        }
        return super.decide(command, state, portResult, now)
    }
}

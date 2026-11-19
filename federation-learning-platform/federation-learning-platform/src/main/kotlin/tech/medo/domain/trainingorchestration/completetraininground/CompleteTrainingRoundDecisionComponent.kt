package tech.medo.domain.trainingorchestration.completetraininground

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundDecision
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import tech.medo.trainingorchestration.traininground.TrainingRoundState

@Component
class CompleteTrainingRoundDecisionComponent : CompleteTrainingRoundDecision {
    override fun decide(command: CompleteTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        if (state.nextRoundId != null) return emptyList()
        require(state.evaluatedSampleCount != null) { "Global model evaluation is required before completion." }
        return super.decide(command, state)
    }
}

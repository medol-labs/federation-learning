package tech.medo.domain.trainingorchestration.submitglobalmodelevaluation

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationDecision
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult
import tech.medo.trainingorchestration.traininground.TrainingRoundState

@Component
class SubmitGlobalModelEvaluationDecisionComponent : SubmitGlobalModelEvaluationDecision {
    override fun decide(command: SubmitGlobalModelEvaluationCommand, state: TrainingRoundState, portResult: SubmitGlobalModelEvaluationResult): List<Any> {
        if (state.evaluatedSampleCount != null) return emptyList()
        return super.decide(command, state, portResult)
    }
}

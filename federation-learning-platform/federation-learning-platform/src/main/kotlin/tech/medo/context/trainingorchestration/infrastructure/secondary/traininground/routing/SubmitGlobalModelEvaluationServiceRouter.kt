package tech.medo.trainingorchestration.infrastructure.secondary.traininground.routing

import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationService
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SubmitGlobalModelEvaluationServiceRouter(private val adapters: ObjectProvider<SubmitGlobalModelEvaluationService>) : SubmitGlobalModelEvaluationService {
    override fun supports(input: SubmitGlobalModelEvaluationInput): Boolean = true

    override fun execute(input: SubmitGlobalModelEvaluationInput): SubmitGlobalModelEvaluationResult {
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
            0 -> error("No SubmitGlobalModelEvaluationService adapter supports the requested input.")
            else -> error("Multiple SubmitGlobalModelEvaluationService adapters support the requested input.")
        }
    }
}

package tech.medo.infrastructure.secondary.trainingorchestration.traininground.submitglobalmodelevaluation

import java.math.BigDecimal
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationService

@Component
class LocalSubmitGlobalModelEvaluationAdapter : SubmitGlobalModelEvaluationService {
    private val log = LoggerFactory.getLogger(LocalSubmitGlobalModelEvaluationAdapter::class.java)

    override fun supports(input: SubmitGlobalModelEvaluationInput): Boolean = true

    override fun execute(input: SubmitGlobalModelEvaluationInput): SubmitGlobalModelEvaluationResult {
        log.info(
            "Submitted local global model evaluation. trainingJobId={}, roundId={}, aggregatedModelId={}, artifactUri={}",
            input.trainingJobId,
            input.roundId,
            input.aggregatedModelId,
            input.aggregatedModelArtifactUri
        )

        return SubmitGlobalModelEvaluationResult.Succeeded(
            globalAccuracy = BigDecimal("0.9000"),
            globalFairnessScore = BigDecimal("1.0000")
        )
    }
}

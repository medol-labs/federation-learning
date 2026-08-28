package tech.medo.trainingorchestration.submitglobalmodelevaluation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationService
import tech.medo.trainingorchestration.traininground.TrainingRoundState




@Component
class SubmitGlobalModelEvaluationCommandHandler(
    private val decision: SubmitGlobalModelEvaluationDecision,
    private val submitGlobalModelEvaluationService: SubmitGlobalModelEvaluationService
) {
    @CommandHandler
    fun handle(
        command: SubmitGlobalModelEvaluationCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        val input = SubmitGlobalModelEvaluationInput(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri)
        val portResult = submitGlobalModelEvaluationService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

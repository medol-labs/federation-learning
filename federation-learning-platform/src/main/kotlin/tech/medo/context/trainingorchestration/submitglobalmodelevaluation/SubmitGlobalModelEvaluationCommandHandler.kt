package tech.medo.trainingorchestration.submitglobalmodelevaluation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class SubmitGlobalModelEvaluationCommandHandler(
    private val decision: SubmitGlobalModelEvaluationDecision
) {
    @CommandHandler
    fun handle(
        command: SubmitGlobalModelEvaluationCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

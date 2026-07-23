package tech.medo.trainingorchestration.completesecureaggregation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.completesecureaggregation.CompleteSecureAggregationCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class CompleteSecureAggregationCommandHandler(
    private val decision: CompleteSecureAggregationDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteSecureAggregationCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

package tech.medo.trainingorchestration.requestsecureaggregation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState




@Component
class RequestSecureAggregationCommandHandler(
    private val decision: RequestSecureAggregationDecision
) {
    @CommandHandler
    fun handle(
        command: RequestSecureAggregationCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

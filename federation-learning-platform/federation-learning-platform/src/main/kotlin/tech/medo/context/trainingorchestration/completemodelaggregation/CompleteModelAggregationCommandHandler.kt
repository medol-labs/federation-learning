package tech.medo.trainingorchestration.completemodelaggregation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.completemodelaggregation.CompleteModelAggregationCommand

import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class CompleteModelAggregationCommandHandler(
    private val decision: CompleteModelAggregationDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteModelAggregationCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

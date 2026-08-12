package tech.medo.trainingorchestration.createtrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState




@Component
class CreateTrainingJobCommandHandler(
    private val decision: CreateTrainingJobDecision
) {
    @CommandHandler
    fun handle(
        command: CreateTrainingJobCommand,
        @InjectEntity(idProperty = "trainingRunConfigurationId") trainingRunConfigurationState: TrainingRunConfigurationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, trainingRunConfigurationState))
    }
}

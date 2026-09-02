package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState



@Component
class UpdateTrainingRunConfigurationCommandHandler(
    private val decision: UpdateTrainingRunConfigurationDecision
) {
    @CommandHandler
    fun handle(
        command: UpdateTrainingRunConfigurationCommand,
        @InjectEntity(idProperty = "trainingRunConfigurationId") state: TrainingRunConfigurationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

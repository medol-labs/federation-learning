package tech.medo.trainingorchestration.locktrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.locktrainingrunconfiguration.LockTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState



@Component
class LockTrainingRunConfigurationCommandHandler(
    private val decision: LockTrainingRunConfigurationDecision
) {
    @CommandHandler
    fun handle(
        command: LockTrainingRunConfigurationCommand,
        @InjectEntity(idProperty = "trainingRunConfigurationId") state: TrainingRunConfigurationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

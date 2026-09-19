package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand

import tech.medo.trainingorchestration.runtimeengineprofile.RuntimeEngineProfileState



@Component
class DefineTrainingRunConfigurationCommandHandler(
    private val decision: DefineTrainingRunConfigurationDecision
) {
    @CommandHandler
    fun handle(
        command: DefineTrainingRunConfigurationCommand,
        @InjectEntity(idProperty = "runtimeEngineProfileId") state: RuntimeEngineProfileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

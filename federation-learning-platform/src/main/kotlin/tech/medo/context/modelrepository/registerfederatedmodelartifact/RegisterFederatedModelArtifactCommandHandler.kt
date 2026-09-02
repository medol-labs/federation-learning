package tech.medo.modelrepository.registerfederatedmodelartifact

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modelrepository.registerfederatedmodelartifact.RegisterFederatedModelArtifactCommand

import tech.medo.modelrepository.modelartifact.ModelArtifactState



@Component
class RegisterFederatedModelArtifactCommandHandler(
    private val decision: RegisterFederatedModelArtifactDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterFederatedModelArtifactCommand,
        @InjectEntity(idProperty = "selection") state: ModelArtifactState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

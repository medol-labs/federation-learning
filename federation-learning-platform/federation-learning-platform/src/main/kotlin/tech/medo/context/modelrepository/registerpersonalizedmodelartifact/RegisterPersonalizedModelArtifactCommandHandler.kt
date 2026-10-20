package tech.medo.modelrepository.registerpersonalizedmodelartifact

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modelrepository.registerpersonalizedmodelartifact.RegisterPersonalizedModelArtifactCommand



import tech.medo.modelrepository.modelartifact.ModelArtifactState



@Component
class RegisterPersonalizedModelArtifactCommandHandler(
    private val decision: RegisterPersonalizedModelArtifactDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterPersonalizedModelArtifactCommand,
        @InjectEntity(idProperty = "selection") state: ModelArtifactState,
        eventAppender: EventAppender
    ) {

        eventAppender.append(decision.decide(command, state))

    }
}

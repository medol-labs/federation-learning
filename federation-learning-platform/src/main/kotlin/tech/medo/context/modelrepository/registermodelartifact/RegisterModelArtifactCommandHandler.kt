package tech.medo.modelrepository.registermodelartifact

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactInput
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactService



@Component
class RegisterModelArtifactCommandHandler(
    private val decision: RegisterModelArtifactDecision,
    private val registerModelArtifactService: RegisterModelArtifactService
) {
    @CommandHandler
    fun handle(
        command: RegisterModelArtifactCommand,
        eventAppender: EventAppender
    ) {
        val input = RegisterModelArtifactInput(modelId = command.modelId, modelName = command.modelName, modelVersion = command.modelVersion, sourceType = command.sourceType, sourceLocation = command.sourceLocation, modelFormat = command.modelFormat)
        val portResult = registerModelArtifactService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}

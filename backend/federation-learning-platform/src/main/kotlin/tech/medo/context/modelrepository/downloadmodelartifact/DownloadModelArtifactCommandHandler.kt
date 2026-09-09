package tech.medo.modelrepository.downloadmodelartifact

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactCommand
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactInput
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactService
import tech.medo.modelrepository.modelartifact.ModelArtifactState



@Component
class DownloadModelArtifactCommandHandler(
    private val decision: DownloadModelArtifactDecision,
    private val downloadModelArtifactService: DownloadModelArtifactService
) {
    @CommandHandler
    fun handle(
        command: DownloadModelArtifactCommand,
        @InjectEntity(idProperty = "selection") state: ModelArtifactState,
        eventAppender: EventAppender
    ) {
        val input = DownloadModelArtifactInput(modelId = command.modelId)
        val portResult = downloadModelArtifactService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}

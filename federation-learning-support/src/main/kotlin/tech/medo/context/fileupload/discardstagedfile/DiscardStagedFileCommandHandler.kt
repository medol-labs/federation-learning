package tech.medo.fileupload.discardstagedfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.discardstagedfile.DiscardStagedFileCommand

import tech.medo.fileupload.stagedfile.StagedFileState




@Component
class DiscardStagedFileCommandHandler(
    private val decision: DiscardStagedFileDecision
) {
    @CommandHandler
    fun handle(
        command: DiscardStagedFileCommand,
        @InjectEntity(idProperty = "stagedFileId") state: StagedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

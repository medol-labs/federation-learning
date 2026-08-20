package tech.medo.fileupload.expirestagedfile

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.expirestagedfile.ExpireStagedFileCommand

import tech.medo.fileupload.stagedfile.StagedFileState




@Component
class ExpireStagedFileCommandHandler(
    private val decision: ExpireStagedFileDecision
) {
    @CommandHandler
    fun handle(
        command: ExpireStagedFileCommand,
        @InjectEntity(idProperty = "stagedFileId") state: StagedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

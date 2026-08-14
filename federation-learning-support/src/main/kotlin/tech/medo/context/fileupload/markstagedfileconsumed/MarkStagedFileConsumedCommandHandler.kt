package tech.medo.fileupload.markstagedfileconsumed

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.fileupload.markstagedfileconsumed.MarkStagedFileConsumedCommand

import tech.medo.fileupload.stagedfile.StagedFileState



@Component
class MarkStagedFileConsumedCommandHandler(
    private val decision: MarkStagedFileConsumedDecision
) {
    @CommandHandler
    fun handle(
        command: MarkStagedFileConsumedCommand,
        @InjectEntity(idProperty = "stagedFileId") state: StagedFileState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

package tech.medo.fileupload.stagefileupload

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.fileupload.stagefileupload.StageFileUploadCommand





@Component
class StageFileUploadCommandHandler(
    private val decision: StageFileUploadDecision
) {
    @CommandHandler
    fun handle(
        command: StageFileUploadCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

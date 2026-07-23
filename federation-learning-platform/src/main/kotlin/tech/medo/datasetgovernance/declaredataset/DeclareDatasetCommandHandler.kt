package tech.medo.datasetgovernance.declaredataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.declaredataset.DeclareDatasetCommand




@Component
class DeclareDatasetCommandHandler(
    private val decision: DeclareDatasetDecision
) {
    @CommandHandler
    fun handle(
        command: DeclareDatasetCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

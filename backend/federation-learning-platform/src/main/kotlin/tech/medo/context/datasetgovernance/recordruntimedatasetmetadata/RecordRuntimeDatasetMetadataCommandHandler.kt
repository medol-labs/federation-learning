package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand





@Component
class RecordRuntimeDatasetMetadataCommandHandler(
    private val decision: RecordRuntimeDatasetMetadataDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeDatasetMetadataCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}

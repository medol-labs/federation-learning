package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand

import tech.medo.datasetgovernance.dataset.DatasetState



@Component
class RecordRuntimeDatasetMetadataCommandHandler(
    private val decision: RecordRuntimeDatasetMetadataDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeDatasetMetadataCommand,
        @InjectEntity(idProperty = "selection") state: DatasetState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

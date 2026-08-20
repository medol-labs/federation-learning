package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata.RecordRuntimeDatasetReprofiledMetadataCommand

import tech.medo.datasetgovernance.runtimedatasetmetadata.RuntimeDatasetMetadataState




@Component
class RecordRuntimeDatasetReprofiledMetadataCommandHandler(
    private val decision: RecordRuntimeDatasetReprofiledMetadataDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeDatasetReprofiledMetadataCommand,
        @InjectEntity(idProperty = "metadataReportId") state: RuntimeDatasetMetadataState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

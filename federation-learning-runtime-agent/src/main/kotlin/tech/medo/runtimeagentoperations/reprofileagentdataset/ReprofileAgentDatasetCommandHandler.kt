package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand

import tech.medo.runtimeagentoperations.runtimedatasetmetadata.RuntimeDatasetMetadataState



@Component
class ReprofileAgentDatasetCommandHandler(
    private val decision: ReprofileAgentDatasetDecision
) {
    @CommandHandler
    fun handle(
        command: ReprofileAgentDatasetCommand,
        @InjectEntity(idProperty = "metadataReportId") state: RuntimeDatasetMetadataState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}

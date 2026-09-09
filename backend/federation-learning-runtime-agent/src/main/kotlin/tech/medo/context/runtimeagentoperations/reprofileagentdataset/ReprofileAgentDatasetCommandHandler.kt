package tech.medo.runtimeagentoperations.reprofileagentdataset

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetCommand
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetInput
import tech.medo.runtimeagentoperations.reprofileagentdataset.ReprofileAgentDatasetService
import tech.medo.runtimeagentoperations.agentdatasetprofile.AgentDatasetProfileState
import tech.medo.runtimeagentoperations.domain.states.AgentDatasetProfileStateEnum


@Component
class ReprofileAgentDatasetCommandHandler(
    private val decision: ReprofileAgentDatasetDecision,
    private val reprofileAgentDatasetService: ReprofileAgentDatasetService
) {
    @CommandHandler
    fun handle(
        command: ReprofileAgentDatasetCommand,
        @InjectEntity(idProperty = "runtimeDatasetBindingId") state: AgentDatasetProfileState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == AgentDatasetProfileStateEnum.REPORTED) {
            "ReprofileAgentDataset requires AgentDatasetProfile to be Reported."
        }
        val input = ReprofileAgentDatasetInput(metadataReportId = command.metadataReportId, runtimeDatasetBindingId = command.runtimeDatasetBindingId)
        val portResult = reprofileAgentDatasetService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}

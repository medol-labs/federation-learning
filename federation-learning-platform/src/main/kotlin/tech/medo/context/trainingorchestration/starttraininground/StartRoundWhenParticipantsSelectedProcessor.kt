package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory

@Component
class StartRoundWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    private val log = LoggerFactory.getLogger(StartRoundWhenParticipantsSelectedProcessor::class.java)

    @DisallowReplay
    @EventHandler
    fun on(event: TrainingRoundParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> {
        log.info(
            "StartRoundWhenParticipantsSelectedProcessor. minimumNodesPerRound={}, selectedRuntimeCount={}",
            event.minimumNodesPerRound,
            event.selectedRuntimeCount
        )
        if (event.minimumNodesPerRound > 0 && event.selectedRuntimeCount >= event.minimumNodesPerRound && event.selectedParticipants.isNotEmpty()) {
            return commandGateway.send(StartTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedParticipants = event.selectedParticipants, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound)).resultMessage
        } else {
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }
    }
}

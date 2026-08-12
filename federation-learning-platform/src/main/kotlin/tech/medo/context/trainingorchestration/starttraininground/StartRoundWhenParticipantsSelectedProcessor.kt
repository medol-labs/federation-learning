package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component

@Component
class StartRoundWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    @DisallowReplay
    @EventHandler
    fun on(event: TrainingRoundParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.minimumNodesPerRound > 0 && event.selectedRuntimeCount >= event.minimumNodesPerRound && event.selectedParticipants.isNotEmpty()) {
            commandGateway.send(StartTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedParticipants = event.selectedParticipants, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

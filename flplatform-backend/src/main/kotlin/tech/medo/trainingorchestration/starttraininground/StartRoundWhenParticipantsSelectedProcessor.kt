package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import java.util.UUID;
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class StartRoundWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<StartTrainingRoundCommand> =
        commandGateway.send(StartTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, selectedOrganizationIds = event.selectedOrganizationIds, selectedRuntimeIds = event.selectedRuntimeIds, selectedParticipants = event.selectedParticipants, selectedOrganizationCount = event.selectedOrganizationCount, selectedRuntimeCount = event.selectedRuntimeCount, minimumNodesPerRound = event.minimumNodesPerRound)).resultMessage.thenApply { it.payload() as StartTrainingRoundCommand }
}

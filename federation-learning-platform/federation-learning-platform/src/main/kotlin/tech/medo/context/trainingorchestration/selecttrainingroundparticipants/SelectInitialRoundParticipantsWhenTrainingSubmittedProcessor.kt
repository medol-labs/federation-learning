package tech.medo.trainingorchestration.selecttrainingroundparticipants

import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SelectInitialRoundParticipantsWhenTrainingSubmittedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingJobSubmittedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = event.trainingJobId, federationId = event.federationId, federationName = event.federationName, trainingRunConfigurationId = event.trainingRunConfigurationId, configurationName = event.configurationName, featureSchemaId = event.featureSchemaId, featureDomain = event.featureDomain, featureSchemaVersion = event.featureSchemaVersion, trainingJobObjective = event.trainingJobObjective)).resultMessage
}

package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionRetryRequestedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-retry-training-round-participant-selection")
@Component
class SelectTrainingRoundParticipantsWhenRetryRequestedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingRoundParticipantSelectionRetryRequestedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SelectTrainingRoundParticipantsCommand(trainingJobId = event.trainingJobId, federationId = java.util.UUID.randomUUID() /* TODO: provide federationId */, federationName = null /* TODO: provide federationName */, trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: provide trainingRunConfigurationId */, configurationName = null /* TODO: provide configurationName */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, featureDomain = null /* TODO: provide featureDomain */, featureSchemaVersion = null /* TODO: provide featureSchemaVersion */, trainingJobObjective = "" /* TODO: provide trainingJobObjective */)).resultMessage
}

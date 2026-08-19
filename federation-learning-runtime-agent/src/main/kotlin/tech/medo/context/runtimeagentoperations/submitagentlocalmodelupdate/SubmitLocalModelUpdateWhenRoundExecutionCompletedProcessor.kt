package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SubmitLocalModelUpdateWhenRoundExecutionCompletedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RoundExecutionCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SubmitAgentLocalModelUpdateCommand(executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, roundExecutionId = event.roundExecutionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, runtimeId = event.runtimeId, featureSchemaId = event.featureSchemaId, localModelId = java.util.UUID.randomUUID() /* TODO: provide localModelId */, updateArtifactId = java.util.UUID.randomUUID() /* TODO: provide updateArtifactId */, artifactRef = event.localUpdateArtifactRef!!, artifactDigest = "" /* TODO: provide artifactDigest */, trainingLoss = event.trainingLoss!!)).resultMessage
}

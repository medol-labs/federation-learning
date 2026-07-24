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
        commandGateway.send(SubmitAgentLocalModelUpdateCommand(modelUpdateSubmissionId = java.util.UUID.randomUUID() /* TODO: provide modelUpdateSubmissionId */, executionSessionId = event.executionSessionId, executionPlanId = event.executionPlanId, roundExecutionId = event.roundExecutionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, runtimeId = event.runtimeId, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, localModelVersionId = java.util.UUID.randomUUID() /* TODO: provide localModelVersionId */, updateArtifactId = java.util.UUID.randomUUID() /* TODO: provide updateArtifactId */, artifactRef = "" /* TODO: provide artifactRef */, artifactDigest = "" /* TODO: provide artifactDigest */, trainingLoss = java.math.BigDecimal.ZERO /* TODO: provide trainingLoss */)).resultMessage
}

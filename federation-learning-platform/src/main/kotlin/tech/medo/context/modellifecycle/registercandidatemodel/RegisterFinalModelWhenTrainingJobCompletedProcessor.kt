package tech.medo.modellifecycle.registercandidatemodel

import tech.medo.trainingorchestration.events.TrainingJobCompletedEvent
import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class RegisterFinalModelWhenTrainingJobCompletedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: TrainingJobCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(RegisterCandidateModelCommand(modelVersionId = java.util.UUID.randomUUID() /* TODO: provide modelVersionId */, trainingJobId = event.trainingJobId, finalRoundId = event.finalRoundId, modelArtifactId = java.util.UUID.randomUUID() /* TODO: provide modelArtifactId */, modelHash = "" /* TODO: provide modelHash */, evaluationReportId = java.util.UUID.randomUUID() /* TODO: provide evaluationReportId */, finalGlobalAccuracy = java.math.BigDecimal.ZERO /* TODO: provide finalGlobalAccuracy */)).resultMessage
}

package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class GeneratePlanForSelectedRuntimeProcessor(private val commandGateway: CommandGateway) {
    @DisallowReplay
    @EventHandler
    fun on(event: TrainingRoundStartedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.selectedRuntimeCount > 0 && event.selectedParticipants.isNotEmpty()) {
            CompletableFuture.allOf(
                *event.selectedParticipants.map { participant ->
                    commandGateway.send(
                        GenerateParticipantExecutionPlanCommand(
                            trainingJobId = event.trainingJobId,
                            trainingRunConfigurationId = event.trainingRunConfigurationId,
                            featureSchemaId = event.featureSchemaId,
                            roundId = event.roundId,
                            roundNumber = event.roundNumber,
                            runtimeId = participant.runtimeId,
                            organizationId = participant.organizationId,
                            baseModelVersionId = java.util.UUID.randomUUID() /* TODO: provide baseModelVersionId */
                        )
                    ).resultMessage
                }.toTypedArray()
            )
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}

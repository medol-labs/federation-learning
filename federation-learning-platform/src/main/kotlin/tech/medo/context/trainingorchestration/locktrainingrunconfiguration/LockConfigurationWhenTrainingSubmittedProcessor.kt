package tech.medo.trainingorchestration.locktrainingrunconfiguration

import tech.medo.trainingorchestration.events.TrainingJobSubmittedEvent
import tech.medo.trainingorchestration.locktrainingrunconfiguration.LockTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LockConfigurationWhenTrainingSubmittedProcessor(
    private val commandGateway: CommandGateway,
    private val trainingJobDashboard: TrainingJobDashboardReadModelRepository,
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository
) {
    private val log = LoggerFactory.getLogger(LockConfigurationWhenTrainingSubmittedProcessor::class.java)

    @DisallowReplay
    @EventHandler
    fun on(event: TrainingJobSubmittedEvent): java.util.concurrent.CompletableFuture<*> {
        val trainingRunConfigurationId = trainingJobDashboard.findProjectionById(event.trainingJobId)
            ?.trainingRunConfigurationId

        if (trainingRunConfigurationId == null) {
            log.warn(
                "Skip locking training run configuration because training job dashboard is missing configuration id. trainingJobId={}",
                event.trainingJobId
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        val trainingRunConfiguration = trainingRunConfigurationCatalog.findProjectionById(trainingRunConfigurationId)
        if (trainingRunConfiguration?.minimumNodesPerRound == null || trainingRunConfiguration.minimumNodesPerRound!! <= 0) {
            log.warn(
                "Skip locking training run configuration because configuration is incomplete. trainingJobId={}, trainingRunConfigurationId={}, minimumNodesPerRound={}",
                event.trainingJobId,
                trainingRunConfigurationId,
                trainingRunConfiguration?.minimumNodesPerRound
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        return commandGateway.send(
            LockTrainingRunConfigurationCommand(
                trainingRunConfigurationId = trainingRunConfigurationId,
                trainingJobId = event.trainingJobId
            )
        ).resultMessage
    }
}

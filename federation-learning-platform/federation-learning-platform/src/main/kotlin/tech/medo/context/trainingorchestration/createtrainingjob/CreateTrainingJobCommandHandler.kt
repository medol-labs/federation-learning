package tech.medo.trainingorchestration.createtrainingjob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import tech.medo.federationmanagement.domain.states.FederationStateEnum
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState



@Component
class CreateTrainingJobCommandHandler(
    private val decision: CreateTrainingJobDecision,
    private val federationOverviewReadModelRepository: FederationOverviewReadModelRepository,
    private val trainingRunConfigurationCatalogReadModelRepository: TrainingRunConfigurationCatalogReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: CreateTrainingJobCommand,
        @InjectEntity(idProperty = "trainingRunConfigurationId") state: TrainingRunConfigurationState,
        eventAppender: EventAppender
    ) {
        val federationOverviewReadModelSelection = federationOverviewReadModelRepository.findById(command.federationId)
        require(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active) {
            "Federation Overview selection is not eligible."
        }
        val trainingRunConfigurationCatalogReadModelSelection = trainingRunConfigurationCatalogReadModelRepository.findById(command.trainingRunConfigurationId)
        require(trainingRunConfigurationCatalogReadModelSelection != null && trainingRunConfigurationCatalogReadModelSelection.state == TrainingRunConfigurationStateEnum.Draft) {
            "Training Run Configuration Catalog selection is not eligible for Create Training Job."
        }
        eventAppender.append(decision.decide(command, state))
    }
}

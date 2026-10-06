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
        if (!(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.createTrainingJob.federationOverview.notEligible",
                args = mapOf(
                    "command" to "Create Training Job",
                    "projection" to "Federation Overview",
                    "field" to "federationId",
                    "profile" to null
                ),
                message = "Federation Overview selection is not eligible."
            )
        }
        val trainingRunConfigurationCatalogReadModelSelection = trainingRunConfigurationCatalogReadModelRepository.findById(command.trainingRunConfigurationId)
        if (!(trainingRunConfigurationCatalogReadModelSelection != null && trainingRunConfigurationCatalogReadModelSelection.state == TrainingRunConfigurationStateEnum.Draft)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.createTrainingJob.trainingRunConfigurationCatalog.notEligible",
                args = mapOf(
                    "command" to "Create Training Job",
                    "projection" to "Training Run Configuration Catalog",
                    "field" to "trainingRunConfigurationId",
                    "profile" to "Create Training Job"
                ),
                message = "Training Run Configuration Catalog selection is not eligible for Create Training Job."
            )
        }
        eventAppender.append(decision.decide(command, state))
    }
}

package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelRepository
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelRepository
import tech.medo.federationmanagement.domain.states.FederationStateEnum
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState



@Component
class UpdateTrainingRunConfigurationCommandHandler(
    private val decision: UpdateTrainingRunConfigurationDecision,
    private val federationOverviewReadModelRepository: FederationOverviewReadModelRepository,
    private val modelArtifactCatalogReadModelRepository: ModelArtifactCatalogReadModelRepository,
    private val runtimeEngineProfileCatalogReadModelRepository: RuntimeEngineProfileCatalogReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: UpdateTrainingRunConfigurationCommand,
        @InjectEntity(idProperty = "trainingRunConfigurationId") state: TrainingRunConfigurationState,
        eventAppender: EventAppender
    ) {
        val federationOverviewReadModelSelection = federationOverviewReadModelRepository.findById(command.federationId)
        if (!(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.updateTrainingRunConfiguration.federationOverview.notEligible",
                args = mapOf(
                    "command" to "Update Training Run Configuration",
                    "projection" to "Federation Overview",
                    "field" to "federationId",
                    "profile" to null
                ),
                message = "Federation Overview selection is not eligible."
            )
        }
        val modelArtifactCatalogReadModelSelection = modelArtifactCatalogReadModelRepository.findById(command.initialModelId)
        if (!(modelArtifactCatalogReadModelSelection != null && modelArtifactCatalogReadModelSelection.state == ModelArtifactStateEnum.Registered)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.updateTrainingRunConfiguration.modelArtifactCatalog.notEligible",
                args = mapOf(
                    "command" to "Update Training Run Configuration",
                    "projection" to "Model Artifact Catalog",
                    "field" to "initialModelId",
                    "profile" to null
                ),
                message = "Model Artifact Catalog selection is not eligible."
            )
        }
        val runtimeEngineProfileCatalogReadModelSelection = runtimeEngineProfileCatalogReadModelRepository.findById(command.runtimeEngineProfileId)
        if (!(runtimeEngineProfileCatalogReadModelSelection != null && runtimeEngineProfileCatalogReadModelSelection.state == RuntimeEngineProfileStateEnum.Registered && runtimeEngineProfileCatalogReadModelSelection.active == true)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.updateTrainingRunConfiguration.runtimeEngineProfileCatalog.notEligible",
                args = mapOf(
                    "command" to "Update Training Run Configuration",
                    "projection" to "Runtime Engine Profile Catalog",
                    "field" to "runtimeEngineProfileId",
                    "profile" to null
                ),
                message = "Runtime Engine Profile Catalog selection is not eligible."
            )
        }
        eventAppender.append(decision.decide(command, state))
    }
}

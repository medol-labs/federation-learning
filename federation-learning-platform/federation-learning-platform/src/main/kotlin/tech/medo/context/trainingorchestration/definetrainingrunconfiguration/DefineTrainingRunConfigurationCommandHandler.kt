package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelRepository
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelRepository
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelRepository
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelRepository
import tech.medo.federationmanagement.domain.states.FederationStateEnum
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum




@Component
class DefineTrainingRunConfigurationCommandHandler(
    private val decision: DefineTrainingRunConfigurationDecision,
    private val federationOverviewReadModelRepository: FederationOverviewReadModelRepository,
    private val featureSchemaCatalogReadModelRepository: FeatureSchemaCatalogReadModelRepository,
    private val modelArtifactCatalogReadModelRepository: ModelArtifactCatalogReadModelRepository,
    private val runtimeEngineProfileCatalogReadModelRepository: RuntimeEngineProfileCatalogReadModelRepository
) {
    @CommandHandler
    fun handle(
        command: DefineTrainingRunConfigurationCommand,
        eventAppender: EventAppender
    ) {
        val federationOverviewReadModelSelection = federationOverviewReadModelRepository.findById(command.federationId)
        if (!(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.defineTrainingRunConfiguration.federationOverview.notEligible",
                args = mapOf(
                    "command" to "Define Training Run Configuration",
                    "projection" to "Federation Overview",
                    "field" to "federationId",
                    "profile" to null
                ),
                message = "Federation Overview selection is not eligible."
            )
        }
        val featureSchemaCatalogReadModelSelection = featureSchemaCatalogReadModelRepository.findById(command.featureSchemaId)
        if (!(featureSchemaCatalogReadModelSelection != null && featureSchemaCatalogReadModelSelection.schemaStatus == "Published")) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.defineTrainingRunConfiguration.featureSchemaCatalog.notEligible",
                args = mapOf(
                    "command" to "Define Training Run Configuration",
                    "projection" to "Feature Schema Catalog",
                    "field" to "featureSchemaId",
                    "profile" to null
                ),
                message = "Feature Schema Catalog selection is not eligible."
            )
        }
        val modelArtifactCatalogReadModelSelection = modelArtifactCatalogReadModelRepository.findById(command.initialModelId)
        if (!(modelArtifactCatalogReadModelSelection != null && modelArtifactCatalogReadModelSelection.state == ModelArtifactStateEnum.Registered)) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "SELECTION_NOT_ELIGIBLE",
                i18nKey = "errors.trainingorchestration.defineTrainingRunConfiguration.modelArtifactCatalog.notEligible",
                args = mapOf(
                    "command" to "Define Training Run Configuration",
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
                i18nKey = "errors.trainingorchestration.defineTrainingRunConfiguration.runtimeEngineProfileCatalog.notEligible",
                args = mapOf(
                    "command" to "Define Training Run Configuration",
                    "projection" to "Runtime Engine Profile Catalog",
                    "field" to "runtimeEngineProfileId",
                    "profile" to null
                ),
                message = "Runtime Engine Profile Catalog selection is not eligible."
            )
        }
        eventAppender.append(decision.decide(command))
    }
}

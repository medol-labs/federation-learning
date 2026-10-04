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
        require(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active) {
            "Federation Overview selection is not eligible."
        }
        val featureSchemaCatalogReadModelSelection = featureSchemaCatalogReadModelRepository.findById(command.featureSchemaId)
        require(featureSchemaCatalogReadModelSelection != null && featureSchemaCatalogReadModelSelection.schemaStatus == "Published") {
            "Feature Schema Catalog selection is not eligible for Define Training Run Configuration."
        }
        val modelArtifactCatalogReadModelSelection = modelArtifactCatalogReadModelRepository.findById(command.initialModelId)
        require(modelArtifactCatalogReadModelSelection != null && modelArtifactCatalogReadModelSelection.state == ModelArtifactStateEnum.Registered) {
            "Model Artifact Catalog selection is not eligible for Define Training Run Configuration."
        }
        val runtimeEngineProfileCatalogReadModelSelection = runtimeEngineProfileCatalogReadModelRepository.findById(command.runtimeEngineProfileId)
        require(runtimeEngineProfileCatalogReadModelSelection != null && runtimeEngineProfileCatalogReadModelSelection.state == RuntimeEngineProfileStateEnum.Registered && runtimeEngineProfileCatalogReadModelSelection.active == true) {
            "Runtime Engine Profile Catalog selection is not eligible for Define Training Run Configuration."
        }
        eventAppender.append(decision.decide(command))
    }
}

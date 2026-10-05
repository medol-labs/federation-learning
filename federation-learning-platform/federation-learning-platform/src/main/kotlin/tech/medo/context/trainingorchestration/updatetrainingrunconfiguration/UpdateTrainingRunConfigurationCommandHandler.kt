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
        require(federationOverviewReadModelSelection != null && federationOverviewReadModelSelection.state == FederationStateEnum.Active) {
            "Federation Overview selection is not eligible."
        }
        val modelArtifactCatalogReadModelSelection = modelArtifactCatalogReadModelRepository.findById(command.initialModelId)
        require(modelArtifactCatalogReadModelSelection != null && modelArtifactCatalogReadModelSelection.state == ModelArtifactStateEnum.Registered) {
            "Model Artifact Catalog selection is not eligible."
        }
        val runtimeEngineProfileCatalogReadModelSelection = runtimeEngineProfileCatalogReadModelRepository.findById(command.runtimeEngineProfileId)
        require(runtimeEngineProfileCatalogReadModelSelection != null && runtimeEngineProfileCatalogReadModelSelection.state == RuntimeEngineProfileStateEnum.Registered && runtimeEngineProfileCatalogReadModelSelection.active == true) {
            "Runtime Engine Profile Catalog selection is not eligible."
        }
        eventAppender.append(decision.decide(command, state))
    }
}

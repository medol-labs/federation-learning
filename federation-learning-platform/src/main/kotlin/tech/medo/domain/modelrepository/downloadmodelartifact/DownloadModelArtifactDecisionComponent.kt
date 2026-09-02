package tech.medo.domain.modelrepository.downloadmodelartifact

import org.springframework.stereotype.Component
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactCommand
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactDecision
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import tech.medo.modelrepository.events.ModelArtifactDownloadAuthorizedEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState

@Component
class DownloadModelArtifactDecisionComponent : DownloadModelArtifactDecision {
    override fun decide(
        command: DownloadModelArtifactCommand,
        state: ModelArtifactState,
        portResult: DownloadModelArtifactResult
    ): List<Any> {
        require(state.currentState == ModelArtifactStateEnum.REGISTERED) {
            "Model artifact is not available for download."
        }
        return when (portResult) {
            is DownloadModelArtifactResult.Succeeded -> listOf(
                ModelArtifactDownloadAuthorizedEvent(
                    modelId = command.modelId,
                    modelName = portResult.modelName,
                    modelVersion = portResult.modelVersion,
                    modelFormat = portResult.modelFormat,
                    modelArtifactDigest = portResult.modelArtifactDigest,
                    downloadUri = portResult.downloadUri
                )
            )
        }
    }
}

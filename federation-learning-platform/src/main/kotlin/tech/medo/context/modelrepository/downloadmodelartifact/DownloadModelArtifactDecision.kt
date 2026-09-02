package tech.medo.modelrepository.downloadmodelartifact

import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactCommand

import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import tech.medo.modelrepository.events.ModelArtifactDownloadAuthorizedEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface DownloadModelArtifactDecision {
    fun decide(command: DownloadModelArtifactCommand, state: ModelArtifactState, portResult: DownloadModelArtifactResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is DownloadModelArtifactResult.Succeeded -> listOf(ModelArtifactDownloadAuthorizedEvent(modelId = command.modelId, modelName = command.modelName, modelVersion = command.modelVersion, modelFormat = portResult.modelFormat, modelArtifactDigest = portResult.modelArtifactDigest, downloadUri = portResult.downloadUri))
                }
    }
}

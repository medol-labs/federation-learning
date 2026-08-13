package tech.medo.modelrepository.registermodelartifact

import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactResult
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface RegisterModelArtifactDecision {
    fun decide(command: RegisterModelArtifactCommand, portResult: RegisterModelArtifactResult): List<Any> {
        return when (portResult) {
                    is RegisterModelArtifactResult.Succeeded -> listOf(ModelArtifactRegisteredEvent(modelId = command.modelId, modelName = command.modelName, modelVersion = command.modelVersion, sourceType = command.sourceType, modelArtifactUri = portResult.modelArtifactUri, modelRegistryRef = portResult.modelRegistryRef, modelFormat = command.modelFormat ?: "" /* TODO: provide non-null modelFormat */, modelArtifactDigest = portResult.modelArtifactDigest, modelSignatureUri = portResult.modelSignatureUri, modelSizeBytes = portResult.modelSizeBytes))
                }
    }
}

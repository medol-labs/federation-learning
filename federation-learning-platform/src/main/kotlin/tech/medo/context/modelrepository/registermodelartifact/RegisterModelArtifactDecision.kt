package tech.medo.modelrepository.registermodelartifact

import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand

import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface RegisterModelArtifactDecision {
    fun decide(command: RegisterModelArtifactCommand): List<Any> {
        return listOf(
            ModelArtifactRegisteredEvent(modelId = command.modelId, modelName = command.modelName, modelVersion = command.modelVersion, sourceType = command.sourceType, modelArtifactUri = "" /* TODO: derive value */, modelRegistryRef = "" /* TODO: derive value */, modelFormat = command.modelFormat ?: "" /* TODO: provide non-null modelFormat */, modelArtifactDigest = "" /* TODO: derive value */, modelSignatureUri = null /* TODO: derive value */, modelSizeBytes = null /* TODO: derive value */)
        )
    }
}

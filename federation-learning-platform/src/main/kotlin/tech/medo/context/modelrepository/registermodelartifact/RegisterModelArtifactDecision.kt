package tech.medo.modelrepository.registermodelartifact

import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand

import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface RegisterModelArtifactDecision {
    fun decide(command: RegisterModelArtifactCommand): List<Any> {
        return listOf(
            ModelArtifactRegisteredEvent(modelVersionId = command.modelVersionId, modelArtifactRef = command.modelArtifactRef, modelRepositoryRef = command.modelRepositoryRef, modelFormat = command.modelFormat, modelHash = command.modelHash, modelSignatureRef = command.modelSignatureRef, modelSizeBytes = command.modelSizeBytes, sourceType = command.sourceType)
        )
    }
}

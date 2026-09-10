package tech.medo.modelrepository.registerfederatedmodelartifact

import tech.medo.modelrepository.registerfederatedmodelartifact.RegisterFederatedModelArtifactCommand


import tech.medo.modelrepository.events.FederatedModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface RegisterFederatedModelArtifactDecision {
    fun decide(command: RegisterFederatedModelArtifactCommand, state: ModelArtifactState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            FederatedModelArtifactRegisteredEvent(modelId = command.modelId, modelName = command.modelName, modelVersion = command.modelVersion, modelDescription = command.modelDescription, sourceType = command.sourceType, modelArtifactUri = command.modelArtifactUri, modelRegistryRef = command.modelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, modelSignatureUri = command.modelSignatureUri, modelSizeBytes = command.modelSizeBytes, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundId = command.roundId)
        )
    }
}

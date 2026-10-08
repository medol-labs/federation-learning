package tech.medo.modelrepository.registerpersonalizedmodelartifact

import tech.medo.modelrepository.registerpersonalizedmodelartifact.RegisterPersonalizedModelArtifactCommand


import tech.medo.modelrepository.events.PersonalizedModelArtifactRegisteredEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState





interface RegisterPersonalizedModelArtifactDecision {
    fun decide(command: RegisterPersonalizedModelArtifactCommand, state: ModelArtifactState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            PersonalizedModelArtifactRegisteredEvent(modelId = command.modelId, modelName = command.modelName, modelPlugin = command.modelPlugin, modelVersion = command.modelVersion, modelDescription = command.modelDescription, sourceType = command.sourceType, modelScope = command.modelScope, modelArtifactUri = command.modelArtifactUri, modelRegistryRef = command.modelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, modelSignatureUri = command.modelSignatureUri, modelSizeBytes = command.modelSizeBytes, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundId = command.roundId, personalizationMode = command.personalizationMode, personalizationTargetType = command.personalizationTargetType, personalizationTargetId = command.personalizationTargetId, personalizationCohortId = command.personalizationCohortId, baseGlobalModelId = command.baseGlobalModelId)
        )
    }
}

package tech.medo.modellifecycle.registercandidatemodel

import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand


import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.model.ModelState





interface RegisterCandidateModelDecision {
    fun decide(command: RegisterCandidateModelCommand): List<Any> {
        return listOf(
            ModelCandidateRegisteredEvent(modelId = command.modelId, trainingJobId = command.trainingJobId, finalRoundId = command.finalRoundId, modelArtifactId = command.modelArtifactId, modelArtifactDigest = command.modelArtifactDigest, evaluationReportId = command.evaluationReportId, finalGlobalAccuracy = command.finalGlobalAccuracy)
        )
    }
}

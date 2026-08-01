package tech.medo.modellifecycle.registercandidatemodel

import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand

import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.modelversion.ModelVersionState





interface RegisterCandidateModelDecision {
    fun decide(command: RegisterCandidateModelCommand): List<Any> {
        return listOf(
            ModelCandidateRegisteredEvent(modelVersionId = command.modelVersionId, trainingJobId = command.trainingJobId, finalRoundId = command.finalRoundId, modelArtifactId = command.modelArtifactId, modelHash = command.modelHash, evaluationReportId = command.evaluationReportId, finalGlobalAccuracy = command.finalGlobalAccuracy)
        )
    }
}

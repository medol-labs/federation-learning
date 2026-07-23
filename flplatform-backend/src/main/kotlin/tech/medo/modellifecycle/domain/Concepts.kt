package tech.medo.modellifecycle.domain

object Concepts {
    data object ModelVersion {
        const val NAME = "ModelVersion"
        val slices = listOf("RegisterCandidateModel", "RecordModelEvaluationPackage", "ApproveModel", "PromoteModelToProduction", "RollbackModelVersion", "RetireModelVersion", "ModelVersionCatalog")
        val states = listOf("Candidate", "EvaluationPackaged", "Approved", "Production", "RolledBack", "Retired")
    }
}

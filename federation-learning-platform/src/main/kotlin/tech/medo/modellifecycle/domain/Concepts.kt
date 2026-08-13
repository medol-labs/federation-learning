package tech.medo.modellifecycle.domain

object Concepts {
    data object Model {
        const val NAME = "Model"
        val slices = listOf("RegisterCandidateModel", "RecordModelEvaluationPackage", "ApproveModel", "PromoteModelToProduction", "RollbackModel", "RetireModel", "ModelCatalog")
        val states = listOf("Candidate", "EvaluationPackaged", "Approved", "Production", "RolledBack", "Retired")
    }
}

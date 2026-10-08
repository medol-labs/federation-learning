package tech.medo.modelrepository.domain

object Concepts {
    data object ModelArtifact {
        const val NAME = "ModelArtifact"
        val slices = listOf("RegisterModelArtifact", "RegisterFederatedModelArtifact", "RegisterPersonalizedModelArtifact", "DownloadModelArtifact", "ModelArtifactCatalog")
        val states = listOf("Registered")
    }
}

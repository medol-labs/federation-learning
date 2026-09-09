package tech.medo.modelrepository.domain

object Concepts {
    data object ModelArtifact {
        const val NAME = "ModelArtifact"
        val slices = listOf("RegisterModelArtifact", "RegisterFederatedModelArtifact", "DownloadModelArtifact", "ModelArtifactCatalog")
        val states = listOf("Registered")
    }
}

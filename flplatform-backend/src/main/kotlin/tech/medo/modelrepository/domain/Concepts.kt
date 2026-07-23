package tech.medo.modelrepository.domain

object Concepts {
    data object ModelArtifact {
        const val NAME = "ModelArtifact"
        val slices = listOf("RegisterModelArtifact", "ModelArtifactCatalog")
        val states = listOf("Registered")
    }
}

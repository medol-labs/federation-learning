package tech.medo.fileupload.domain

object Concepts {
    data object StagedFile {
        const val NAME = "StagedFile"
        val slices = listOf("StageFileUpload", "MarkStagedFileConsumed", "DiscardStagedFile", "ExpireStagedFile", "StagedFileCatalog")
        val states = listOf("Staged", "Consumed", "Discarded", "Expired")
    }
}

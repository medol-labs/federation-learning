package tech.medo.fileupload.domain

object Concepts {
    data object UploadedFile {
        const val NAME = "UploadedFile"
        val slices = listOf("UploadFile", "MarkFileReferenced", "DownloadFile", "DiscardFile", "ExpireFile", "UploadedFileCatalog")
        val states = listOf("Available", "Referenced", "Discarded", "Expired")
    }
}

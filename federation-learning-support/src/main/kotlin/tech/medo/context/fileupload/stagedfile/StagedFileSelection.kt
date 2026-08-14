package tech.medo.fileupload.stagedfile

import java.util.UUID;


data class StagedFileSelection(
    val stagedFileId: UUID
)

object StagedFileTags {
    const val STAGED_FILE_ID = "stagedFileId"
}

object StagedFileMetadata {
    val concepts = listOf("StagedFile")
}

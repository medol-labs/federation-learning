package tech.medo.infrastructure.secondary.fileupload.uploadedfile.downloadfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.fileupload.downloadfile.DownloadFileInput
import tech.medo.fileupload.downloadfile.DownloadFileResult
import tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage.LocalUploadedFileStorage
import java.nio.file.Path
import java.util.UUID

class LocalDownloadFileAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun resolvesStoredFileMetadataAndPublicDownloadUri() {
        val storage = LocalUploadedFileStorage(tempDir.toString())
        storage.save(
            fileId = FILE_ID,
            originalFileName = "global-model.json",
            contentType = "application/json",
            inputStream = "model-content".byteInputStream()
        )
        val adapter = LocalDownloadFileAdapter(storage, "http://support:8080/")

        val result = adapter.execute(DownloadFileInput(FILE_ID)) as DownloadFileResult.Succeeded

        assertEquals("global-model.json", result.originalFileName)
        assertEquals("application/json", result.contentType)
        assertEquals(13, result.sizeBytes)
        assertEquals("http://support:8080/api/files/$FILE_ID/content", result.downloadUri)
    }

    @Test
    fun rejectsMissingStoredFile() {
        val adapter = LocalDownloadFileAdapter(LocalUploadedFileStorage(tempDir.toString()), "http://support:8080")

        assertThrows(IllegalStateException::class.java) {
            adapter.execute(DownloadFileInput(FILE_ID))
        }
    }

    private companion object {
        private val FILE_ID: UUID = UUID.fromString("11111111-1111-4111-8111-111111111111")
    }
}

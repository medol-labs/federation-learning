package tech.medo.infrastructure.secondary.fileupload.stagedfile.stagefileupload

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.infrastructure.secondary.fileupload.stagedfile.storage.LocalStagedFileStorage
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

class LocalStageFileUploadAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun stagesLocalFile() {
        val source = tempDir.resolve("model.bin")
        Files.writeString(source, "model payload")
        val adapter = LocalStageFileUploadAdapter(LocalStagedFileStorage(tempDir.resolve("staged").toString()), 30)

        val result = Files.newInputStream(source).use {
            adapter.save(
                uploadId = STAGED_FILE_ID.toString(),
                fieldName = "uploadedFile",
                originalFileName = "model.bin",
                contentType = "application/octet-stream",
                inputStream = it
            )
        }

        val stagedPath = Path.of(URI.create(result.location))
        assertTrue(Files.exists(stagedPath))
        assertEquals("model.bin", result.originalFileName)
        assertEquals("application/octet-stream", result.contentType)
        assertEquals(Files.readString(source), Files.readString(stagedPath))
        assertEquals(sha256(source), result.checksum)
        assertTrue(result.expiresAt.isAfter(LocalDateTime.now()))
    }

    @Test
    fun generatesUploadIdWhenNotProvided() {
        val source = tempDir.resolve("dataset.csv")
        Files.writeString(source, "x,y\n1,0")
        val adapter = LocalStageFileUploadAdapter(LocalStagedFileStorage(tempDir.resolve("staged").toString()), 30)

        val result = Files.newInputStream(source).use {
            adapter.save(
                uploadId = null,
                fieldName = "uploadedFile",
                originalFileName = "dataset.csv",
                contentType = "text/csv",
                inputStream = it
            )
        }

        assertTrue(Files.exists(Path.of(URI.create(result.location))))
        assertEquals("dataset.csv", result.originalFileName)
        assertEquals("text/csv", result.contentType)
        assertTrue(result.expiresAt.isAfter(LocalDateTime.now()))
    }

    private fun sha256(path: Path): String {
        val digest = MessageDigest.getInstance("SHA-256")
        Files.newInputStream(path).use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (true) {
                val read = input.read(buffer)
                if (read < 0) {
                    break
                }
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private companion object {
        private val STAGED_FILE_ID: UUID = UUID.fromString("11111111-1111-4111-8111-111111111111")
    }
}

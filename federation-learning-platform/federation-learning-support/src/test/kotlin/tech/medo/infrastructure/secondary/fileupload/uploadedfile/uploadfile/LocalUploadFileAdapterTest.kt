package tech.medo.infrastructure.secondary.fileupload.uploadedfile.uploadfile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage.LocalUploadedFileStorage
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

class LocalUploadFileAdapterTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun storesUploadedFile() {
        val source = tempDir.resolve("model.bin")
        Files.writeString(source, "model payload")
        val storage = LocalUploadedFileStorage(tempDir.resolve("stored").toString())
        val adapter = LocalUploadFileAdapter(storage, 30, "http://support")

        val result = Files.newInputStream(source).use {
            adapter.save(
                uploadId = FILE_ID.toString(),
                fieldName = "uploadedFile",
                originalFileName = "model.bin",
                contentType = "application/octet-stream",
                inputStream = it
            )
        }

        assertEquals("http://support/api/files/$FILE_ID/content", result.location)
        val storedContent = requireNotNull(storage.open(FILE_ID))
        assertEquals("model.bin", result.originalFileName)
        assertEquals("application/octet-stream", result.contentType)
        storedContent.inputStream.use { assertEquals(Files.readAllBytes(source).toList(), it.readBytes().toList()) }
        assertEquals(sha256(source), result.checksum)
        assertTrue(result.expiresAt.isAfter(LocalDateTime.now()))
    }

    @Test
    fun generatesUploadIdWhenNotProvided() {
        val source = tempDir.resolve("dataset.csv")
        Files.writeString(source, "x,y\n1,0")
        val adapter = LocalUploadFileAdapter(
            LocalUploadedFileStorage(tempDir.resolve("stored").toString()),
            30,
            "http://support"
        )

        val result = Files.newInputStream(source).use {
            adapter.save(
                uploadId = null,
                fieldName = "uploadedFile",
                originalFileName = "dataset.csv",
                contentType = "text/csv",
                inputStream = it
            )
        }

        assertTrue(result.location.startsWith("http://support/api/files/"))
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
        private val FILE_ID: UUID = UUID.fromString("11111111-1111-4111-8111-111111111111")
    }
}

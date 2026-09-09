package tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.Properties
import java.util.UUID
import kotlin.io.path.name

@Component
class LocalUploadedFileStorage(
    @Value("\${file-upload.local.root-directory:\${java.io.tmpdir}/medo/file-upload}")
    rootDirectory: String
) : UploadedFileStorage {
    private val log = LoggerFactory.getLogger(LocalUploadedFileStorage::class.java)
    private val rootPath: Path = Path.of(rootDirectory).toAbsolutePath().normalize()

    override fun save(
        fileId: UUID,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StoredUploadedFile {
        val targetDirectory = rootPath.resolve(fileId.toString()).normalize()
        require(targetDirectory.startsWith(rootPath)) {
            "Resolved uploaded file directory escapes storage root."
        }
        Files.createDirectories(targetDirectory)

        val safeFileName = sanitizeFileName(originalFileName.ifBlank { "uploaded-file" })
        val target = targetDirectory.resolve(safeFileName).normalize()
        require(target.startsWith(rootPath)) {
            "Resolved uploaded file path escapes storage root."
        }

        inputStream.use { Files.copy(it, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING) }
        val sizeBytes = Files.size(target)
        val checksum = sha256(target)
        val stored = StoredUploadedFile(
            fileId = fileId,
            originalFileName = originalFileName.ifBlank { safeFileName },
            contentType = contentType ?: Files.probeContentType(target),
            sizeBytes = sizeBytes,
            location = target.toUri().toString(),
            checksum = checksum
        )
        writeMetadata(targetDirectory, stored)
        log.info(
            "Stored uploaded file locally. fileId={}, location={}, sizeBytes={}",
            fileId,
            stored.location,
            sizeBytes
        )
        return stored
    }

    override fun metadata(location: String): StoredUploadedFile? {
        val target = resolveLocation(location) ?: return null
        if (!Files.isRegularFile(target)) {
            return null
        }

        val metadata = readMetadata(target.parent)
        return StoredUploadedFile(
            fileId = metadata?.getProperty("fileId")?.let(UUID::fromString)
                ?: target.parent.fileName.name.let(UUID::fromString),
            originalFileName = metadata?.getProperty("originalFileName") ?: target.fileName.name,
            contentType = metadata?.getProperty("contentType")?.takeIf { it.isNotBlank() } ?: Files.probeContentType(target),
            sizeBytes = Files.size(target),
            location = target.toUri().toString(),
            checksum = metadata?.getProperty("checksum")?.takeIf { it.isNotBlank() } ?: sha256(target)
        )
    }

    override fun metadata(fileId: UUID): StoredUploadedFile? {
        val directory = resolveDirectory(fileId) ?: return null
        val properties = readMetadata(directory) ?: return null
        val fileName = properties.getProperty("storedFileName")
            ?: sanitizeFileName(properties.getProperty("originalFileName") ?: "uploaded-file")
        val target = directory.resolve(fileName).normalize()
        return metadata(target.toUri().toString())
    }

    override fun open(location: String): UploadedFileContent? {
        val target = resolveLocation(location) ?: return null
        if (!Files.isRegularFile(target)) {
            return null
        }
        val metadata = metadata(location) ?: return null
        return UploadedFileContent(
            metadata = metadata,
            inputStream = Files.newInputStream(target)
        )
    }

    override fun open(fileId: UUID): UploadedFileContent? {
        val metadata = metadata(fileId) ?: return null
        return open(metadata.location)
    }

    private fun resolveLocation(location: String): Path? =
        runCatching {
            val value = location.trim()
            val path = if (value.startsWith("file:")) {
                Path.of(URI.create(value))
            } else {
                Path.of(value)
            }.toAbsolutePath().normalize()
            path.takeIf { it.startsWith(rootPath) }
        }.getOrNull()

    private fun resolveDirectory(fileId: UUID): Path? {
        val directory = rootPath.resolve(fileId.toString()).normalize()
        return directory.takeIf { it.startsWith(rootPath) && Files.isDirectory(it) }
    }

    private fun writeMetadata(directory: Path, stored: StoredUploadedFile) {
        val properties = Properties().apply {
            setProperty("fileId", stored.fileId.toString())
            setProperty("originalFileName", stored.originalFileName)
            setProperty("storedFileName", Path.of(URI.create(stored.location)).fileName.toString())
            setProperty("contentType", stored.contentType ?: "")
            setProperty("checksum", stored.checksum)
        }
        Files.newOutputStream(directory.resolve(METADATA_FILE_NAME)).use {
            properties.store(it, null)
        }
    }

    private fun readMetadata(directory: Path): Properties? {
        val metadata = directory.resolve(METADATA_FILE_NAME)
        if (!Files.isRegularFile(metadata)) {
            return null
        }
        return Properties().also { properties ->
            Files.newInputStream(metadata).use(properties::load)
        }
    }

    private fun sanitizeFileName(fileName: String): String =
        fileName.substringAfterLast('/').substringAfterLast('\\')
            .replace(Regex("[^A-Za-z0-9._-]"), "_")
            .ifBlank { "uploaded-file" }

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
        private const val METADATA_FILE_NAME = ".metadata.properties"
    }
}

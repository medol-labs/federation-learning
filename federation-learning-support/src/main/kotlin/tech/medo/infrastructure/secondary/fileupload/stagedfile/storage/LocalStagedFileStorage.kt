package tech.medo.infrastructure.secondary.fileupload.stagedfile.storage

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
class LocalStagedFileStorage(
    @Value("\${file-upload.local.root-directory:./var/file-upload}")
    rootDirectory: String
) : StagedFileStorage {
    private val log = LoggerFactory.getLogger(LocalStagedFileStorage::class.java)
    private val rootPath: Path = Path.of(rootDirectory).toAbsolutePath().normalize()

    override fun save(
        stagedFileId: UUID,
        originalFileName: String,
        contentType: String?,
        inputStream: InputStream
    ): StoredStagedFile {
        val targetDirectory = rootPath.resolve(stagedFileId.toString()).normalize()
        require(targetDirectory.startsWith(rootPath)) {
            "Resolved staged file directory escapes storage root."
        }
        Files.createDirectories(targetDirectory)

        val safeFileName = sanitizeFileName(originalFileName.ifBlank { "uploaded-file" })
        val target = targetDirectory.resolve(safeFileName).normalize()
        require(target.startsWith(rootPath)) {
            "Resolved staged file path escapes storage root."
        }

        inputStream.use { Files.copy(it, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING) }
        val sizeBytes = Files.size(target)
        val checksum = sha256(target)
        val stored = StoredStagedFile(
            stagedFileId = stagedFileId,
            originalFileName = originalFileName.ifBlank { safeFileName },
            contentType = contentType ?: Files.probeContentType(target),
            sizeBytes = sizeBytes,
            location = target.toUri().toString(),
            checksum = checksum
        )
        writeMetadata(targetDirectory, stored)
        log.info(
            "Stored staged file locally. stagedFileId={}, location={}, sizeBytes={}",
            stagedFileId,
            stored.location,
            sizeBytes
        )
        return stored
    }

    override fun metadata(location: String): StoredStagedFile? {
        val target = resolveLocation(location) ?: return null
        if (!Files.isRegularFile(target)) {
            return null
        }

        val metadata = readMetadata(target.parent)
        return StoredStagedFile(
            stagedFileId = metadata?.getProperty("stagedFileId")?.let(UUID::fromString)
                ?: target.parent.fileName.name.let(UUID::fromString),
            originalFileName = metadata?.getProperty("originalFileName") ?: target.fileName.name,
            contentType = metadata?.getProperty("contentType")?.takeIf { it.isNotBlank() } ?: Files.probeContentType(target),
            sizeBytes = Files.size(target),
            location = target.toUri().toString(),
            checksum = metadata?.getProperty("checksum")?.takeIf { it.isNotBlank() } ?: sha256(target)
        )
    }

    override fun open(location: String): StagedFileContent? {
        val target = resolveLocation(location) ?: return null
        if (!Files.isRegularFile(target)) {
            return null
        }
        val metadata = metadata(location) ?: return null
        return StagedFileContent(
            metadata = metadata,
            inputStream = Files.newInputStream(target)
        )
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

    private fun writeMetadata(directory: Path, stored: StoredStagedFile) {
        val properties = Properties().apply {
            setProperty("stagedFileId", stored.stagedFileId.toString())
            setProperty("originalFileName", stored.originalFileName)
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

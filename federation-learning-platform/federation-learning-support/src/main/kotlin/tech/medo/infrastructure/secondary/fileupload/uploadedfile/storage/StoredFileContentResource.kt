package tech.medo.infrastructure.secondary.fileupload.uploadedfile.storage

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("/api/files")
class StoredFileContentResource(private val storage: UploadedFileStorage) {
    @PreAuthorize("hasAuthority('*:*')")
    @GetMapping("/{fileId}/content")
    fun download(@PathVariable fileId: UUID): ResponseEntity<InputStreamResource> {
        val content = storage.open(fileId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file $fileId was not found.")
        val headers = HttpHeaders().apply {
            contentDisposition = ContentDisposition.attachment()
                .filename(content.metadata.originalFileName)
                .build()
            contentLength = content.metadata.sizeBytes
        }
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(content.metadata.contentType?.let(MediaType::parseMediaType) ?: MediaType.APPLICATION_OCTET_STREAM)
            .body(InputStreamResource(content.inputStream))
    }
}

package tech.medo.fileupload.uploadfile

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/uploadedfile")
class UploadFileResource(
    private val commandGateway: CommandGateway,
    private val uploadFileUploadedFileStorage: UploadFileUploadedFileStorage
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('upload_file:execute')")
    @PostMapping("/uploadfile/file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun UploadFileFile(
        @RequestPart("uploadedFile") uploadedFile: MultipartFile,
        @RequestParam("fileId", required = false) fileId: UUID?,
        @RequestParam("purpose") purpose: String,
        request: HttpServletRequest
    ): CompletableFuture<UploadFileCommand> {
        val resolvedFileId = fileId ?: UUID.randomUUID()
        val uploadedFileStored = uploadFileUploadedFileStorage.save(
            uploadId = resolvedFileId.toString(),
            fieldName = "uploadedFile",
            originalFileName = uploadedFile.originalFilename ?: "uploaded-file",
            contentType = uploadedFile.contentType,
            inputStream = uploadedFile.inputStream
        )
        val command = UploadFileCommand(
            fileId = resolvedFileId,
            uploadedFile = uploadedFileStored.location,
            originalFileName = uploadedFileStored.originalFileName,
            contentType = uploadedFileStored.contentType,
            sizeBytes = uploadedFileStored.sizeBytes,
            fileLocation = uploadedFileStored.location,
            checksum = uploadedFileStored.checksum,
            expiresAt = uploadedFileStored.expiresAt,
            purpose = purpose
        )
        return commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
    }
}

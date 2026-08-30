package tech.medo.fileupload.stagefileupload

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
@RequestMapping("/stagedfile")
class StageFileUploadResource(
    private val commandGateway: CommandGateway,
    private val stageFileUploadUploadedFileStorage: StageFileUploadUploadedFileStorage
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('stage_file_upload:execute')")
    @PostMapping("/stagefileupload/file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun StageFileUploadFile(
        @RequestPart("uploadedFile") uploadedFile: MultipartFile,
        @RequestParam("stagedFileId", required = false) stagedFileId: UUID?,
        @RequestParam("purpose") purpose: String,
        request: HttpServletRequest
    ): CompletableFuture<StageFileUploadCommand> {
        val uploadedFileStored = stageFileUploadUploadedFileStorage.save(
            uploadId = stagedFileId?.toString(),
            fieldName = "uploadedFile",
            originalFileName = uploadedFile.originalFilename ?: "uploaded-file",
            contentType = uploadedFile.contentType,
            inputStream = uploadedFile.inputStream
        )
        val command = StageFileUploadCommand(
            stagedFileId = stagedFileId ?: UUID.randomUUID(),
            uploadedFile = uploadedFileStored.location,
            originalFileName = uploadedFileStored.originalFileName,
            contentType = uploadedFileStored.contentType,
            sizeBytes = uploadedFileStored.sizeBytes,
            stagedFileLocation = uploadedFileStored.location,
            checksum = uploadedFileStored.checksum,
            expiresAt = uploadedFileStored.expiresAt,
            purpose = purpose
        )
        return commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
    }
}

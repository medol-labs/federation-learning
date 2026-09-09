package tech.medo.fileupload.uploadfile

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.fileupload.uploadfile.UploadFileCommand
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:upload-file-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class UploadFileIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun UploadFileForModelImport() {
        val command = UploadFileCommand(
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            uploadedFile = "file://credit-risk-initial.onnx",
            originalFileName = "credit-risk-initial.onnx",
            contentType = "application/octet-stream",
            sizeBytes = 2048L,
            fileLocation = "file://uploads/file-1/credit-risk-initial.onnx",
            checksum = "sha256:model",
            expiresAt = java.time.LocalDateTime.parse("2026-07-25T12:00:00"),
            purpose = "MODEL_ARTIFACT_IMPORT"
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

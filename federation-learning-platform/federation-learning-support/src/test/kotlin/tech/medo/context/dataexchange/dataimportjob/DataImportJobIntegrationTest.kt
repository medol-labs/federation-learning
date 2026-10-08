package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.dataexchange.dataimportjob.RequestDataImportCommand
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:data-import-job-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DataImportJobIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RequestDataImportintegration() {
        val command = RequestDataImportCommand(
            dataImportJobId = java.util.UUID.randomUUID(),
            commandName = "",
            resourceName = "",
            originalFileName = "",
            filePath = "",
            requestedLocale = null,
            requestedAt = java.time.LocalDateTime.now(),
            requestHash = "",
            status = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

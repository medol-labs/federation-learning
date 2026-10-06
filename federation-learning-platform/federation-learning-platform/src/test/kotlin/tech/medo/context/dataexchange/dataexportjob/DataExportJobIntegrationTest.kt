package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.dataexchange.dataexportjob.RequestDataExportCommand
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:data-export-job-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DataExportJobIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RequestDataExportintegration() {
        val command = RequestDataExportCommand(
            dataExportJobId = java.util.UUID.randomUUID(),
            resourceName = "",
            criteriaJson = "",
            sortJson = "",
            columnsJson = "",
            requestedLocale = null,
            requestedAt = java.time.LocalDateTime.now(),
            snapshotUpperBound = java.time.LocalDateTime.now(),
            requestHash = "",
            fileName = "",
            status = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

package tech.medo.runtimemonitoring.appendaudittrail

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimemonitoring.appendaudittrail.AppendAuditTrailCommand
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:append-audit-trail-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class AppendAuditTrailIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun AppendAuditTrailintegration() {
        val command = AppendAuditTrailCommand(
            auditRecordId = java.util.UUID.randomUUID(),
            sourceEventName = "",
            sourceEntityId = null,
            severity = "",
            payloadHash = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

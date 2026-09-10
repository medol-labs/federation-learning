package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimemonitoring.detectruntimeagentoffline.DetectRuntimeAgentOfflineCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:detect-runtime-agent-offline-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DetectRuntimeAgentOfflineIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DetectRuntimeAgentOfflineintegration() {
        val command = DetectRuntimeAgentOfflineCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            federationId = null,
            federationName = null,
            trainingJobId = null,
            trainingJobObjective = null,
            roundExecutionId = null,
            runtimeNodeName = null,
            offlineReason = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

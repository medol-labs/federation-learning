package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:report-runtime-instance-connected-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ReportRuntimeInstanceConnectedIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ReportRuntimeInstanceConnectedintegration() {
        val command = ReportRuntimeInstanceConnectedCommand(
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeAgentEndpoint = "http://localhost:8082",
            endpointScope = "LOCAL",
            runtimePlatformConnectionReady = false,
            platformApiReachable = false,
            agentAuthenticationSucceeded = false,
            controlChannelEstablished = false,
            heartbeatAccepted = false
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

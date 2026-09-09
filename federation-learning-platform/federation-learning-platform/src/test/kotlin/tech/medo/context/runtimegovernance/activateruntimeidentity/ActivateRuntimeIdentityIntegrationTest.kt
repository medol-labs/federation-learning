package tech.medo.runtimegovernance.activateruntimeidentity

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:activate-runtime-identity-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ActivateRuntimeIdentityIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ActivateRuntimeIdentityintegration() {
        val command = ActivateRuntimeIdentityCommand(
            runtimeId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeName = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

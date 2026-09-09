package tech.medo.federationmanagement.createfederation

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.federationmanagement.createfederation.CreateFederationCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:create-federation-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class CreateFederationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun CreateFederationintegration() {
        val command = CreateFederationCommand(
            federationId = java.util.UUID.randomUUID(),
            federationName = "",
            description = "",
            minimumParticipantCount = 0
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

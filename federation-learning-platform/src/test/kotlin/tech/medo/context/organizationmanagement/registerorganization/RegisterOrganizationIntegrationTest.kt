package tech.medo.organizationmanagement.registerorganization

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.organizationmanagement.registerorganization.RegisterOrganizationCommand
import java.util.UUID
import tech.medo.organizationmanagement.domain.types.OrganizationType

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-organization-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterOrganizationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RegisterOrganizationintegration() {
        val command = RegisterOrganizationCommand(
            organizationId = java.util.UUID.randomUUID(),
            organizationName = "",
            organizationType = OrganizationType.HOSPITAL,
            contactEmail = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

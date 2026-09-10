package tech.medo.organizationmanagement.binduseraccounttoorganization

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.organizationmanagement.binduseraccounttoorganization.BindUserAccountToOrganizationCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:bind-user-account-to-organization-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class BindUserAccountToOrganizationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun BindUserAccountToOrganizationintegration() {
        val command = BindUserAccountToOrganizationCommand(
            userOrganizationMembershipId = java.util.UUID.randomUUID(),
            userAccountId = java.util.UUID.randomUUID(),
            username = null,
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            organizationUserRole = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

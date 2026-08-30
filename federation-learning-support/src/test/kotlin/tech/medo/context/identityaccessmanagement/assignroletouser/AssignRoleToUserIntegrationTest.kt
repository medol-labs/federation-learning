package tech.medo.identityaccessmanagement.assignroletouser

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:assign-role-to-user-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class AssignRoleToUserIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun AssignRoleToUserintegration() {
        val command = AssignRoleToUserCommand(
            userAccountId = java.util.UUID.randomUUID(),
            roleCodes = listOf("ADMIN")
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

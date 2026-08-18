package tech.medo.runtimeagentoperations.profileagentdataset

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:profile-agent-dataset-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ProfileAgentDatasetIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ProfileAgentDatasetintegration() {
        val command = ProfileAgentDatasetCommand(
            metadataReportId = java.util.UUID.randomUUID(),
            runtimeDatasetBindingId = java.util.UUID.randomUUID(),
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = "credit-risk",
            runtimeId = java.util.UUID.randomUUID()
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

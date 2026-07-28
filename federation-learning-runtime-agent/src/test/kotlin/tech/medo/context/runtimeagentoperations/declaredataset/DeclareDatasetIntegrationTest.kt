package tech.medo.runtimeagentoperations.declaredataset

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:declare-dataset-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DeclareDatasetIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DeclareDatasetintegration() {
        val command = DeclareDatasetCommand(
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = "",
            datasetType = "",
            datasetUsage = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

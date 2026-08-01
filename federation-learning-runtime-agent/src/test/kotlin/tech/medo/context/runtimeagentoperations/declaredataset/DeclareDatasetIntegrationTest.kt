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
    fun DeclareDatasetWithFeatureSchemaSnapshot() {
        val command = DeclareDatasetCommand(
            datasetId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            datasetName = "credit-risk",
            datasetType = "TABULAR",
            datasetUsage = "TRAINING"
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

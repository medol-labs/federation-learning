package tech.medo.datasetgovernance.definefeatureschema

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.datasetgovernance.definefeatureschema.DefineFeatureSchemaCommand
import java.util.UUID
import tech.medo.datasetgovernance.domain.types.FeatureDefinition
import tech.medo.datasetgovernance.domain.types.LabelDefinition

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:define-feature-schema-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DefineFeatureSchemaIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DefineFeatureSchemaintegration() {
        val command = DefineFeatureSchemaCommand(
            featureSchemaId = java.util.UUID.randomUUID(),
            featureDomain = "",
            version = "",
            dataModality = "",
            features = emptyList(),
            labels = emptyList()
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

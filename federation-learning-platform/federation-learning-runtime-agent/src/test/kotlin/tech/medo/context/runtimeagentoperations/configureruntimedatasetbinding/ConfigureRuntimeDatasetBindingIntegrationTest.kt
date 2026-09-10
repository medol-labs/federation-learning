package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:configure-runtime-dataset-binding-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ConfigureRuntimeDatasetBindingIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ConfigureRuntimeDatasetBindingintegration() {
        val command = ConfigureRuntimeDatasetBindingCommand(
            runtimeDatasetBindingId = java.util.UUID.randomUUID(),
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            organizationName = null,
            featureDomain = null,
            featureSchemaVersion = null,
            datasetName = "",
            runtimeId = java.util.UUID.randomUUID(),
            runtimeName = null,
            dataSourceType = "",
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = null,
            objectBucket = null,
            objectPrefix = null,
            dataFormat = "",
            credentialSecretName = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

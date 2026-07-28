package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand
import java.util.UUID;
import java.math.BigDecimal;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:record-runtime-dataset-metadata-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RecordRuntimeDatasetMetadataIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RecordRuntimeDatasetMetadataintegration() {
        val command = RecordRuntimeDatasetMetadataCommand(
            metadataReportId = java.util.UUID.randomUUID(),
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            sampleCount = 0,
            featureCount = 0,
            schemaCompatible = null,
            labelCompatible = null,
            missingValueRate = null,
            duplicateRate = null,
            qualityScore = null,
            nonIidScore = null,
            classBalanceScore = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

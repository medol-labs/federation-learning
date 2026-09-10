package tech.medo.runtimemonitoring.recordruntimenodeinventory

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimemonitoring.recordruntimenodeinventory.RecordRuntimeNodeInventoryCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:record-runtime-node-inventory-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RecordRuntimeNodeInventoryIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RecordRuntimeNodeInventoryintegration() {
        val command = RecordRuntimeNodeInventoryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeNodeInventoryReportId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeName = null,
            runtimeNodeName = "",
            infrastructureNodeId = null,
            runtimeNodeRole = "",
            nodeReady = false,
            runtimeEngineVersion = null,
            containerEngineVersion = null,
            operatingSystem = null,
            architecture = "",
            inventoryHash = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

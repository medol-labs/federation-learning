package tech.medo.runtimeagentoperations.reportagentruntimenodeinventory

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.reportagentruntimenodeinventory.ReportAgentRuntimeNodeInventoryCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:report-agent-runtime-node-inventory-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ReportAgentRuntimeNodeInventoryIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ReportAgentRuntimeNodeInventoryintegration() {
        val command = ReportAgentRuntimeNodeInventoryCommand(
            runtimeNodeInventoryReportId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
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

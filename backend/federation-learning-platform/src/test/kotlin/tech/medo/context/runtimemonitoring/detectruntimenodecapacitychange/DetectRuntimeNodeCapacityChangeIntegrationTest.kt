package tech.medo.runtimemonitoring.detectruntimenodecapacitychange

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimemonitoring.detectruntimenodecapacitychange.DetectRuntimeNodeCapacityChangeCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:detect-runtime-node-capacity-change-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DetectRuntimeNodeCapacityChangeIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DetectRuntimeNodeCapacityChangeintegration() {
        val command = DetectRuntimeNodeCapacityChangeCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            previousCapacityHash = null,
            currentCapacityHash = "",
            allocatableCpuCores = 0,
            allocatableMemoryGb = 0,
            allocatableGpuCount = 0
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

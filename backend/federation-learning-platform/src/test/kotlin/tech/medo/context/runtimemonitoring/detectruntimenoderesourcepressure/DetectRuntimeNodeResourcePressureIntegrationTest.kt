package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand
import java.util.UUID
import java.math.BigDecimal

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:detect-runtime-node-resource-pressure-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DetectRuntimeNodeResourcePressureIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DetectRuntimeNodeResourcePressureintegration() {
        val command = DetectRuntimeNodeResourcePressureCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            trainingJobId = null,
            pressureType = "",
            observedValue = java.math.BigDecimal.ZERO,
            thresholdValue = java.math.BigDecimal.ZERO
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

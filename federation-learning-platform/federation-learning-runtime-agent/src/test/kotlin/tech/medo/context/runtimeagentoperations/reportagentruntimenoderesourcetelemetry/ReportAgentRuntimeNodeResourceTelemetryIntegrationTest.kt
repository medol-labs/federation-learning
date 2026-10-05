package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry.ReportAgentRuntimeNodeResourceTelemetryCommand
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:report-agent-runtime-node-resource-telemetry-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ReportAgentRuntimeNodeResourceTelemetryIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ReportAgentRuntimeNodeResourceTelemetryintegration() {
        val command = ReportAgentRuntimeNodeResourceTelemetryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = null,
            runtimeNodeName = null,
            nodeReady = false,
            allocatableCpuCores = 0,
            allocatableMemoryGb = 0,
            allocatableGpuCount = 0,
            allocatedCpuCores = 0,
            allocatedMemoryGb = 0,
            allocatedGpuCount = 0,
            availableCpuCores = 0,
            availableMemoryGb = 0,
            availableGpuCount = 0,
            runningWorkloadCount = 0,
            workloadCapacity = 0,
            observedAt = java.time.LocalDateTime.now(),
            telemetryRetentionPolicy = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

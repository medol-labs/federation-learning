package tech.medo.runtimeagentoperations.reportagentruntimetelemetry

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.reportagentruntimetelemetry.ReportAgentRuntimeTelemetryCommand
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:report-agent-runtime-telemetry-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ReportAgentRuntimeTelemetryIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ReportAgentRuntimeTelemetryintegration() {
        val command = ReportAgentRuntimeTelemetryCommand(
            nodeId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            federationId = null,
            trainingJobId = null,
            roundExecutionId = null,
            cpuLoad = null,
            gpuLoad = null,
            memoryLoad = null,
            lastHeartbeatAt = null,
            telemetryRetentionPolicy = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

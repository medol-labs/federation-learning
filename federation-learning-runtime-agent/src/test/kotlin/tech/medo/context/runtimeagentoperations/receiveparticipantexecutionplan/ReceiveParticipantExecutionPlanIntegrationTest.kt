package tech.medo.runtimeagentoperations.receiveparticipantexecutionplan

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.runtimeagentoperations.receiveparticipantexecutionplan.ReceiveParticipantExecutionPlanCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:receive-participant-execution-plan-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class ReceiveParticipantExecutionPlanIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun ReceiveParticipantExecutionPlanintegration() {
        val command = ReceiveParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

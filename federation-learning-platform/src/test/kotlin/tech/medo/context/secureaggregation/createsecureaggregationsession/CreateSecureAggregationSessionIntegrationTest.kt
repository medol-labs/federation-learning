package tech.medo.secureaggregation.createsecureaggregationsession

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:create-secure-aggregation-session-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class CreateSecureAggregationSessionIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun CreateSecureAggregationSessionintegration() {
        val command = CreateSecureAggregationSessionCommand(
            secureAggregationSessionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            requiredParticipantCount = 0,
            selectedOrganizationIds = emptyList(),
            selectedRuntimeIds = emptyList(),
            selectedOrganizationCount = 0,
            selectedRuntimeCount = 0,
            minimumNodesPerRound = 0,
            secureAggregationRequired = false
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

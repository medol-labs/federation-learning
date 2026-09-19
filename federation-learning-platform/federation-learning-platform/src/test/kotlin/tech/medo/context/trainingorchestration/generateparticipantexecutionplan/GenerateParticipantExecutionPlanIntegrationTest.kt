package tech.medo.trainingorchestration.generateparticipantexecutionplan

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:generate-participant-execution-plan-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class GenerateParticipantExecutionPlanIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun GenerateInitialRoundPlanWithInitialModelSnapshot() {
        val command = GenerateParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 1,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelPlugin = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            runtimeEngineProfileId = java.util.UUID.randomUUID(),
            runtimeEngineProfileName = null,
            runtimeEnginePluginProfile = "",
            runtimeEngineImage = "",
            runtimeEngineImageDigest = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
        )

        commandGateway.send(command).getResultMessage().join()
    }

    @Test
    fun GenerateLaterRoundPlanWithAggregatedModelSnapshot() {
        val command = GenerateParticipantExecutionPlanCommand(
            executionPlanId = java.util.UUID.randomUUID(),
            executionSessionId = java.util.UUID.randomUUID(),
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            configurationName = null,
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-2".toByteArray()),
            roundNumber = 2,
            runtimeId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            baseModelId = java.util.UUID.randomUUID(),
            baseModelArtifactUri = "",
            baseModelRegistryRef = "",
            baseModelPlugin = "",
            baseModelFormat = "",
            baseModelArtifactDigest = "",
            baseModelSignatureUri = null,
            runtimeEngineProfileId = java.util.UUID.randomUUID(),
            runtimeEngineProfileName = null,
            runtimeEnginePluginProfile = "",
            runtimeEngineImage = "",
            runtimeEngineImageDigest = null,
            secureAggregationRequired = false,
            secureAggregationSessionId = null,
            encryptionScheme = null,
            publicKeyVersion = null,
            publicKeyRef = null,
            encryptedParameterScale = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

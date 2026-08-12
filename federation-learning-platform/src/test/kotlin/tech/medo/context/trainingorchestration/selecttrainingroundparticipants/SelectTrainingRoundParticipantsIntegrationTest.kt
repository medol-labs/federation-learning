package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.FederationMembershipSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeDatasetMetadataSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeIdentitySnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.RuntimeInfrastructureAccessSnapshot
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsInput
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsService
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:select-training-round-participants-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class SelectTrainingRoundParticipantsIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @MockitoBean
    lateinit var selectTrainingRoundParticipantsService: SelectTrainingRoundParticipantsService

    @Test
    fun SelectParticipantsWhenRuntimePoolReachesQuorum() {
        val trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray())
        val federationId = UUID.nameUUIDFromBytes("federation-1".toByteArray())
        val featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray())
        val organizationId = UUID.nameUUIDFromBytes("org-1".toByteArray())
        val runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray())
        val runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray())
        `when`(selectTrainingRoundParticipantsService.execute(SelectTrainingRoundParticipantsInput(trainingJobId))).thenReturn(
            SelectTrainingRoundParticipantsResult.Succeeded(
                trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
                federationId = federationId,
                featureSchemaId = featureSchemaId,
                currentRoundNumber = 0,
                minimumNodesPerRound = 1,
                memberships = listOf(
                    FederationMembershipSnapshot(
                        federationId = federationId,
                        organizationId = organizationId,
                        membershipStatus = "Joined"
                    )
                ),
                runtimeIdentities = listOf(
                    RuntimeIdentitySnapshot(
                        runtimeId = runtimeId,
                        runtimeAgentId = runtimeAgentId,
                        organizationId = organizationId,
                        identityStatus = "Active"
                    )
                ),
                runtimeInfrastructureAccesses = listOf(
                    RuntimeInfrastructureAccessSnapshot(
                        runtimeAgentId = runtimeAgentId,
                        state = "CONNECTED"
                    )
                ),
                datasetMetadata = listOf(
                    RuntimeDatasetMetadataSnapshot(
                        datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
                        organizationId = organizationId,
                        runtimeId = runtimeId,
                        featureSchemaId = featureSchemaId,
                        schemaCompatible = true,
                        labelCompatible = true
                    )
                )
            )
        )
        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = trainingJobId
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

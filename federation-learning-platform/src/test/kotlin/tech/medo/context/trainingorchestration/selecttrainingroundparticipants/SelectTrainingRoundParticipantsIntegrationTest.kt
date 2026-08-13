package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant
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
        val featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray())
        val organizationId = UUID.nameUUIDFromBytes("org-1".toByteArray())
        val runtimeId = UUID.nameUUIDFromBytes("runtime-1".toByteArray())
        val datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray())
        `when`(selectTrainingRoundParticipantsService.execute(SelectTrainingRoundParticipantsInput(trainingJobId))).thenReturn(
            SelectTrainingRoundParticipantsResult.Succeeded(
                trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
                featureSchemaId = featureSchemaId,
                roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
                roundNumber = 1,
                minimumNodesPerRound = 1,
                selectedOrganizationIds = listOf(organizationId),
                selectedRuntimeIds = listOf(runtimeId),
                selectedParticipants = listOf(
                    TrainingRoundParticipant(
                        organizationId = organizationId,
                        runtimeId = runtimeId,
                        datasetId = datasetId
                    )
                ),
                selectedOrganizationCount = 1,
                selectedRuntimeCount = 1
            )
        )
        val command = SelectTrainingRoundParticipantsCommand(
            trainingJobId = trainingJobId
        )

        commandGateway.send(command).getResultMessage().join()
    }
}

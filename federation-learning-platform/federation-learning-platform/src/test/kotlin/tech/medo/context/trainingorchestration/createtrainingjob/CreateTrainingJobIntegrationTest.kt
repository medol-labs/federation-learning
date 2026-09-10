package tech.medo.trainingorchestration.createtrainingjob

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:create-training-job-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class CreateTrainingJobIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun CreateTrainingJobWithRunnableConfiguration() {
        val command = CreateTrainingJobCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            federationName = null,
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            configurationName = null,
            featureDomain = null,
            featureSchemaVersion = null,
            objective = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
